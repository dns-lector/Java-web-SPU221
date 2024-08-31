package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import itstep.learning.services.hash.*;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( HashService.class )
                .annotatedWith( Names.named("digest") )
                .to( Md5HashService.class ) ;
        bind( HashService.class )
                .annotatedWith( Names.named("signature") )
                .to( ShaHashService.class ) ;

        bind( String.class )
                .annotatedWith( Names.named("viewsFolder") )
                .toInstance( "views" );
        bind( String.class )
                .annotatedWith( Names.named("resourcesFolder") )
                .toInstance( "resources" );
    }
}

/*
Модуль реєстрації сервісів (служб) універсального типу (не тільки веб-призачення)

 */
