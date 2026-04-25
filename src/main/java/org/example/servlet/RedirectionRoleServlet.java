package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/selectionRole")
public class RedirectionRoleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");

        if (role == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        switch (role.toLowerCase()) {
            case "admin":
                resp.sendRedirect(req.getContextPath() + "/connexion_admin.html");
                break;
            case "doctor":
                resp.sendRedirect(req.getContextPath() + "/connexion_medecin.html");
                break;
            case "patient":
                resp.sendRedirect(req.getContextPath() + "/connexion_patient.html");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/index.html");
                break;
        }
    }
}
