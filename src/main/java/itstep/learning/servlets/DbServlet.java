package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dal.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DbServlet extends HttpServlet {
    private final Connection connection;
    private final UserDao userDao;

    @Inject
    public DbServlet(Connection connection, UserDao userDao) {
        this.connection = connection;
        this.userDao = userDao;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> databases = new ArrayList<>();
        try {
            String sql = "SHOW DATABASES" ;
            Statement stmt = connection.createStatement();   // SqlCommand
            ResultSet res = stmt.executeQuery( sql ) ;   // SqlDataReader
            while( res.next() ) {
                databases.add( res.getString(1) ) ;   // !! нумерація у JDBC - з 1
            }
            res.close();
            stmt.close();
        }
        catch( SQLException ex ) {
            req.setAttribute( "error", ex.getMessage() );
        }

        try {
            userDao.installTable();
            databases.add( "Install Users OK" );
        }
        catch( Exception ex ) {
            req.setAttribute( "error", ex.getMessage() );
        }

        req.setAttribute( "databases", databases );
        req.setAttribute("pageBody", "db.jsp");
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }

}
/*
Д.З. Створити footer у шаблоні сторінок
Створити сторінку "логі" - реалізувати сервлет, представлення, налаштування
Суть сторінки - виведення усіх моментів (дата-час) заходу на цю сторінку.
Відповідно - при кожному заході на сервлет додавати до БД запис з моментом,
 вилучати усі записи і відображати їх на представленні.
* окрім моменту фіксувати саму сторінку (адресу) і логувати всі заходи на сайт.

JSON (API)                                     DAL - Data Access Layer
     \                                          | UserDao |
DB --ORM--> DTO (Data Transfer Obj / Entity) ---| ChatDao |--> Controller...
     /                                          | GoodDao |
File
 */

