package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.*;
import itstep.learning.servlets.*;

public class WebModule extends ServletModule {
    @Override
    protected void configureServlets() {
        // реєструємо фільтри

        // Особливість: фільтр спрацьовує на ресурсні запити (/css, /js тощо)
        // filter( "/*" ).through( CharsetFilter.class );
        // filterRegex - вилучаємо з фільтру css/ та js/
        filterRegex( "^/(?!css/.*|js/.*).*$" ).through( CharsetFilter.class );

        // та сервлети
        serve( "/"     ).with( HomeServlet.class   );
        serve( "/db"   ).with( DbServlet.class     );
        serve( "/user" ).with( UserServlet.class   );
    }
}

/*
Модуль конфігурації веб-сутностей (сервлетів, фільтрів тощо)
Він надає третій варіант реєстрації фільтрів та сервлетів. Для нього
необхідно додати для всіх класів фільтрів та сервлетів анотацію Singleton
а також знімаємо інші форми реєстрації (анотації чи web.xml)
 */
