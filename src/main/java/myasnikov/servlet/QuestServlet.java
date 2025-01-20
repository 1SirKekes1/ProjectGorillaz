package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.entity.QuestStep;
import myasnikov.service.QuestService;
import myasnikov.utility.ImageConverter;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
  private final QuestService questService = new QuestService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String questId = req.getParameter("questId");
    String stepId = req.getParameter("stepId");

    try {
      Long id = Long.parseLong(questId);
      Optional<Quest> questOptional = questService.findById(id);

      if (questOptional.isPresent()) {
        Quest quest = questOptional.get();
        req.setAttribute("quest", quest);

        QuestStep currentStep = getCurrentStep(quest, stepId);
        if (currentStep != null
            && currentStep.getBase64Image() == null
            && currentStep.getImagePath() != null) {
          ImageConverter imageConverter = new ImageConverter();
          String base64Image = imageConverter.convertImageToBase64(currentStep.getImagePath());
          currentStep.setBase64Image(base64Image);
        }

        req.setAttribute("currentStep", currentStep);

        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
      } else {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quest not found");
      }
    } catch (NumberFormatException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quest ID");
    }
  }

  private QuestStep getCurrentStep(Quest quest, String stepId) {
    if (stepId != null && !stepId.isEmpty()) {
      Long stepIdLong = Long.parseLong(stepId);
      return quest.getSteps().stream()
          .filter(step -> step.getId().equals(stepIdLong))
          .findFirst()
          .orElse(null);
    }
    return quest.getSteps().isEmpty() ? null : quest.getSteps().get(0);
  }
}
