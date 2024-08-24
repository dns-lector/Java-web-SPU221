package itstep.learning.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("fromServlet", "HomeServlet");  // ViewData["fromServlet"] = "HomeServlet"
        req.setAttribute("pageBody", "index.jsp");

        // return View()
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }

}

/*
Сервлети - спеціалізовані класи для мережних задач.
У веб-проєктах грають роль контролерів (АРІ-контролерів)
Для підключення сервлету необхідно
або) зареєструвати його у web.xml
або) додати сервлетну анотацію
або) зареєструвати його у службі інжекції
 */