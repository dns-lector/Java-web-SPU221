package itstep.learning.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/db")
public class DbServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Підключення до БД
        // JDBC (~ADO/PDO) - уніфікована технологія доступу до БД
        // підключення - інструкція - результат
        Connection connection = null;
        Driver mysqlDriver = null;
        try {
            mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver( mysqlDriver );
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/JAVA_SPU_221?useUnicode=true&characterEncoding=UTF-8",
                "user_221",
                "pass_221"
            ) ;
            String sql = "SHOW DATABASES" ;
            Statement stmt = connection.createStatement();   // SqlCommand
            ResultSet res = stmt.executeQuery( sql ) ;   // SqlDataReader
            List<String> databases = new ArrayList<>();
            while( res.next() ) {
                databases.add( res.getString(1) ) ;   // !! нумерація у JDBC - з 1
            }
            req.setAttribute( "databases", databases );
            res.close();
            stmt.close();
        }
        catch( SQLException ex ) {
            req.setAttribute( "error", ex.getMessage() );
        }

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
 */