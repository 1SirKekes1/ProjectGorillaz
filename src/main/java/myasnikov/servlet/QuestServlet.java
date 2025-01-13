package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.service.QuestService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/quests")
public class QuestServlet extends HttpServlet {
    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, Quest> quests = questService.findAll();
        req.setAttribute("quests", quests);
        req.getRequestDispatcher("/WEB-INF/quests.jsp").forward(req, resp);
    }
}