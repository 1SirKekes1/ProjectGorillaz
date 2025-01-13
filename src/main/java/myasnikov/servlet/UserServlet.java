package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.User;
import myasnikov.service.UserService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, User> users = userService.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }
}