package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.service.QuestService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@WebServlet("/quests")
public class QuestsServlet extends HttpServlet {
  private final QuestService questService = new QuestService();


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<Quest> quests = questService.findAll();

    quests.forEach(quest -> {
      if (quest.getImageData() != null) {
        String base64Image = Base64.getEncoder().encodeToString(quest.getImageData());
        quest.setImageDataBase64(base64Image);
      }
    });

    req.setAttribute("quests", quests);
    req.getRequestDispatcher("/WEB-INF/quests.jsp").forward(req, resp);
  }
}
