package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
        // TODO: Implement
        // 1. Create new Cookie with name and value
        // 2. Set maxAge
        // 3. Set path to "/"
        // 4. Set httpOnly to true
        // 5. Add cookie to response
        if(response == null){
            throw new IllegalArgumentException("HttpServletResponse cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Cookie name cannot be null or empty");
        }
        Cookie cookie = new Cookie(name, value != null ? value : "");
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        
    }
    /**
     * Get cookie value by name
     * @param request HTTP request
     * @param name Cookie name
     * @return Cookie value or null if not found
     */
    public static String getCookieValue(HttpServletRequest request, String name){
        // TODO: Implement
        // 1. Get all cookies from request
        // 2. Loop through cookies
        // 3. Find cookie with matching name
        // 4. Return value or null
        if (request == null){
            throw new IllegalArgumentException("HttpServletRequest cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Cookie name cannot be null or empty");
        }
        Cookie[] cookies= request.getCookies();
        if(cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
 
    /**
     * Check if cookie exists
     * @param request HTTP request
     * @param name Cookie name
     * @return true if cookie exists
     */
    public static boolean hasCookie(HttpServletRequest request, String name) {
       return getCookieValue(request, name) != null;
    }
    
    /**
     * Delete cookie by setting max age to 0
     * @param response HTTP response
     * @param name Cookie name to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name){
        // TODO: Implement
        // 1. Create cookie with same name and empty value
        // 2. Set maxAge to 0
        // 3. Set path to "/"
        // 4. Add to response
        if (response == null){
            throw new IllegalArgumentException("HttpServletResponse cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Cookie name cannot be null or empty");
        }
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    /**
     * Update existing cookie
     * @param response HTTP response
     * @param name Cookie name
     * @param newValue New cookie value
     * @param maxAge New max age
     */
     public static void updateCookie(HttpServletResponse response, String name,String newValue, int maxAge) {
        // TODO: Implement
        // Simply create a new cookie with same name
        createCookie(response, name, newValue, maxAge);
    }
}
