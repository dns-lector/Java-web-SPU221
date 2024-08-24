package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import itstep.learning.services.hash.HashService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet {
    private final HashService digestHashService;  // MD5
    private final HashService signatureHashService;  // SHA
    private final String viewsFolder;
    private final String resourcesFolder;

    @Inject
    public HomeServlet(
        @Named("digest") HashService digestHashService,
        @Named("signature") HashService signatureHashService,
        @Named("viewsFolder") String viewsFolder,
        @Named("resourcesFolder") String resourcesFolder
    ) {
        this.digestHashService = digestHashService;
        this.signatureHashService = signatureHashService;
        this.viewsFolder = viewsFolder;
        this.resourcesFolder = resourcesFolder;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute( "fromServlet",
                digestHashService.digest("123") +
                " " +
                signatureHashService.digest("123") +
                " " +
                viewsFolder +
                " " +
                resourcesFolder +
                        " " + new String( Character.toChars(0x1F60A) ) );  // ViewData["fromServlet"] = "HomeServlet"
        req.setAttribute( "pageBody", "index.jsp" );

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

Д.З. Реалізувати інжекцію генераторів випадкових рядків кількох типів
- для імені файлу (символи нижнього реєстру, не містить спец.символів / *?.\)
- для криптографічної солі - без обмежень
- для ОТР (one time password) - тільки цифри з довжиною 6 символів
- для постійних паролів - те, що можна набрати з клавіатури (маленькі, великі,
    спец.символи)

 */