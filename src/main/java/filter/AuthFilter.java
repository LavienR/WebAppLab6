package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

import java.io.IOException;

/**
 * Authentication Filter - Checks if user is logged in
 * Protects all pages except login and public resources
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    
    // Public URLs that don't require authentication
    private static final String[] PUBLIC_URLS = {
        "/login",
        "/logout",
        ".css",
        ".js",
        ".png",
        ".jpg",
        ".jpeg",
        ".gif"
    };
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthFilter initialized");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // Check if this is a public URL
        if (isPublicUrl(path)) {
            // Allow access to public URLs
            chain.doFilter(request, response);
            return;
        }
        
        HttpSession session = httpRequest.getSession(false);
        // Check if user isn't logged in
        if (session == null || session.getAttribute("user") == null) {
        String token = null;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("remember_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            if (token != null && !token.trim().isEmpty()) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByToken(token);
                 if (user != null) {
                    session = httpRequest.getSession(true);
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("fullName", user.getFullName());
                    
                    System.out.println("Auto-login successful for user: " + user.getUsername());
                     chain.doFilter(request, response);
                    return;
                } else {
                     Cookie deleteCookie = new Cookie("remember_token", "");
                    deleteCookie.setMaxAge(0);
                    deleteCookie.setPath("/");
                    httpResponse.addCookie(deleteCookie);
                    
                    System.out.println("Invalid remember token detected and deleted");
                }
            }
        }


        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
        if (isLoggedIn) {
            // User is logged in, allow access
            chain.doFilter(request, response);
        } else {
            // User not logged in, redirect to login
            String loginURL = contextPath + "/login";
            httpResponse.sendRedirect(loginURL);
        }
    }
    
    @Override
    public void destroy() {
        System.out.println("AuthFilter destroyed");
    }
    
    /**
     * Check if URL is public (doesn't require authentication)
     */
    private boolean isPublicUrl(String path) {
        for (String publicUrl : PUBLIC_URLS) {
            if (path.contains(publicUrl)) {
                return true;
            }
        }
        return false;
    }
}
