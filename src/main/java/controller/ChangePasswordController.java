package controller;
import dao.StudentDAO;
import dao.UserDAO;
import model.User;
import util.PasswordHasher;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {
     private UserDAO userDAO;
 @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/change_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        //Get current user from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (!user.getPassword().equals(currentPassword)) {
            request.setAttribute("error", "Current password is incorrect");
            request.getRequestDispatcher("/change_password.jsp").forward(request, response);
            return;
        }
        if(newPassword == null || newPassword.length() < 8) {
            request.setAttribute("error", "New password must be at least 8 characters");
            request.getRequestDispatcher("/change_password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirmation do not match");
            request.getRequestDispatcher("/change_password.jsp").forward(request, response);
            return;
        }

        try{
            String hashedNewPassword= PasswordHasher.hash(newPassword);
            //update in DB
            boolean updateSuccess= userDAO.updatePassword(user.getId(), hashedNewPassword);
            if(updateSuccess){
                user.setPassword(hashedNewPassword);
                session.setAttribute("user", user);
                request.setAttribute("message", "Password changed successfully"); 
            }
            else{
                request.setAttribute("error", "Failed to update password");
            }
        }
        catch (Exception e){
            request.setAttribute("error", "An error occurred while changing password");
        }

        request.getRequestDispatcher("/change_password.jsp").forward(request,response);
    }

}
