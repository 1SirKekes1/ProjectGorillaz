package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.EndType;
import myasnikov.entity.Quest;
import myasnikov.entity.QuestStep;
import myasnikov.entity.User;
import myasnikov.service.QuestService;
import myasnikov.service.UserService;
import myasnikov.utility.ImageConverter;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    private final QuestService questService = new QuestService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questId = req.getParameter("questId");
        String stepId = req.getParameter("stepId");

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("errorMessage", "User not logged in, for test purposes try:" + "              \"test\"" + "              \"test\"");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            return;
        }
        Long userId = user.getId();

        try {
            Long id = Long.parseLong(questId);
            Optional<Quest> questOptional = questService.findById(id);

            if (questOptional.isPresent()) {
                Quest quest = questOptional.get();
                req.setAttribute("quest", quest);

                QuestStep currentStep = getCurrentStep(quest, stepId);
                req.setAttribute("currentStep", currentStep);

                if (currentStep != null) {
                    if (currentStep.getEndType() == EndType.WIN) {
                        userService.incrementAttribute(userId, "wins");
                    }
                    if (currentStep.getEndType() == EndType.LOSE) {
                        userService.incrementAttribute(userId, "losses");
                    }
                }

                req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
            } else {
                req.setAttribute("errorMessage", "User not logged in, for test purposes try:\"" + "              \"test\" +\n" + "              \"test\"");
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid quest ID");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    private QuestStep getCurrentStep(Quest quest, String stepId) {
        if (stepId != null && !stepId.isEmpty()) {
            Long stepIdLong = Long.parseLong(stepId);
            return quest.getSteps().stream().filter(step -> step.getId() == stepIdLong).findFirst().orElse(null);
        }
        return quest.getSteps().isEmpty() ? null : quest.getSteps().getFirst();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            return;
        }
        Long userId = user.getId();
        userService.incrementAttribute(userId, "wins");
    }
}
