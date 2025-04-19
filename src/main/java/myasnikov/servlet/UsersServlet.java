package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.User;
import myasnikov.service.UserService;

import java.io.IOException;
import java.util.List;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Long userId = Long.valueOf(req.getParameter("userId"));
        String action = req.getParameter("action");

        User user = userService.findById(userId).orElse(null);
        if (user != null) {

            switch (action) {
                case "incrementGames":
                    user.setGames(user.getGames() + 1);
                    break;
                case "incrementWins":
                    user.setWins(user.getWins() + 1);
                    break;
                case "incrementLosses":
                    user.setLosses(user.getLosses() + 1);
                    break;
                default:
                    break;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/quests");
    }
}
