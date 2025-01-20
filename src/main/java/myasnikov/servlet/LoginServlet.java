package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.config.AppConfig;
import myasnikov.entity.User;
import myasnikov.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  private final UserRepository userRepository = (UserRepository) AppConfig.getUserRepository();
  ;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (user.getPassword().equals(password)) {
        req.getSession().setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(req, resp);
      } else {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid password");
      }
    } else {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
    }
  }
}
