package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.service.QuestService;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/quest")
public class QuestDetailServlet extends HttpServlet {
    private QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questId = req.getParameter("id");
        if (questId == null || questId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quest ID is missing");
            return;
        }

        Long id;
        try {
            id = Long.parseLong(questId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Quest ID");
            return;
        }

        Optional<Quest> questOptional = questService.findById(id);
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            req.setAttribute("quest", quest);
            req.getRequestDispatcher("/WEB-INF/questDetail.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quest not found");
        }
    }
}