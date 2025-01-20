package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.service.QuestService;
import myasnikov.utility.ImageConverter;

import java.io.IOException;
import java.util.Map;

@WebServlet("/quests")
public class QuestsServlet extends HttpServlet {
  private final QuestService questService = new QuestService();
  private final ImageConverter imageConverter = new ImageConverter();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<Long, Quest> quests = questService.findAll();

    for (Quest quest : quests.values()) {
      if (quest.getBase64Image() == null && quest.getImagePath() != null) {
        String base64Image = imageConverter.convertImageToBase64(quest.getImagePath());
        quest.setBase64Image(base64Image);
      }
    }
    req.setAttribute("quests", quests);
    req.getRequestDispatcher("/WEB-INF/quests.jsp").forward(req, resp);
  }
}
