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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questId = req.getParameter("questId");
        String stepId = req.getParameter("stepId");

        Long id = Long.parseLong(questId);


        Optional<Quest> questOptional = questService.findById(id);
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            req.setAttribute("quest", quest);
            QuestStep currentStep = null;
            if (stepId != null && !stepId.isEmpty()) {
                try {
                    Long stepIdLong = Long.parseLong(stepId);
                    currentStep = quest.getSteps().stream()
                            .filter(step -> step.getId().equals(stepIdLong))
                            .findFirst()
                            .orElse(null);

                    if (currentStep.getBase64Image() == null && currentStep.getImagePath() != null) {
                        ImageConverter imageConverter = new ImageConverter();
                        String base64Image = imageConverter.convertImageToBase64(currentStep.getImagePath());
                        currentStep.setBase64Image(base64Image);
                    }

                } catch (NumberFormatException e) {
                    /// TODO ignore wrong id
                }
            }


            // Если текущий шаг не указан, берём первый шаг
            if (currentStep == null && !quest.getSteps().isEmpty()) {
                currentStep = quest.getSteps().get(0);
            }

            req.setAttribute("currentStep", currentStep);

            if (currentStep != null) {
                int currentIndex = quest.getSteps().indexOf(currentStep);

                if (currentIndex > 0) {
                    QuestStep prevStep = quest.getSteps().get(currentIndex - 1);
                    req.setAttribute("prevStepId", prevStep.getId());
                }

                if (currentIndex < quest.getSteps().size() - 1) {
                    QuestStep nextStep = quest.getSteps().get(currentIndex + 1);
                    req.setAttribute("nextStepId", nextStep.getId());
                }
            }

            req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quest not found");
        }
    }
}