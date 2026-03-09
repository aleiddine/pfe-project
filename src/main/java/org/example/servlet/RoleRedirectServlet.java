package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/roleSelect")
public class RoleRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");

        if (role == null) {
            resp.sendRedirect("index.html");
            return;
        }

        switch (role.toLowerCase()) {
            case "admin":
                resp.sendRedirect("login_admin.html");
                break;
            case "doctor":
                resp.sendRedirect("login_medecin.html");
                break;
            case "patient":
                resp.sendRedirect("login_patient.html");
                break;
            default:
                resp.sendRedirect("index.html");
                break;
        }
    }
}
