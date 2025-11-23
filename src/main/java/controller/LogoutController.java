package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(false);
        // Get remember token BEFORE invalidating session
    String token = null;
    Cookie[] cookies = request.getCookies();
    
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("remember_token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
    }


        if (session != null) {
            // Invalidate session
            session.invalidate();
        }
         // Delete remember token from database and cookie
    if (token != null) {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteRememberToken(token);
        
        // Delete cookie
        Cookie deleteCookie = new Cookie("remember_token", "");
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        response.addCookie(deleteCookie);
    }
        
        // Redirect to login page with message
        response.sendRedirect("login?message=You have been logged out successfully");
        response.sendRedirect("login");

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
