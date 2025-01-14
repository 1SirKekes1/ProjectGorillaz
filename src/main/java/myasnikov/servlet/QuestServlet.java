package myasnikov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myasnikov.entity.Quest;
import myasnikov.service.QuestService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

@WebServlet("/quests")
public class QuestServlet extends HttpServlet {
    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, Quest> quests = questService.findAll();

        for (Quest quest : quests.values()) {
            if (quest.getBase64Image() == null && quest.getImagePath() != null) {
                try (InputStream inputStream = getClass().getResourceAsStream(quest.getImagePath())) {
                    if (inputStream != null) {
                        byte[] imageBytes = inputStream.readAllBytes();
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        quest.setBase64Image(base64Image);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        req.setAttribute("quests", quests);
        req.getRequestDispatcher("/WEB-INF/quests.jsp").forward(req, resp);
    }
}