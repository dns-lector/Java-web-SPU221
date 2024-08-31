package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;

public class IocContextListener extends GuiceServletContextListener {
    private DbModule dbModule;

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ServiceModule(),
                new WebModule(),   // , ...  -- перелік модулів
                dbModule = new DbModule()
        );
    }

    @Override
    public void contextDestroyed( ServletContextEvent servletContextEvent ) {
        if( dbModule != null ) {
            dbModule.close();
        }
        super.contextDestroyed( servletContextEvent );
    }
}

/*
Веб-проєкт передбачає циркулювання подій змін життєвого циклу та
підписування на них.

 */