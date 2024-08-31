package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbModule extends AbstractModule {
    private Connection currenConnection = null;
    private Driver mysqlDriver = null;

    @Provides  // методи-провайдери визначають інжекцію за типом повернення
    private Connection getConnection() {
        if (currenConnection == null) {   // якщо раніше не підключались
            Map<String, String> ini = new HashMap<>();
            try( InputStream iniStream = this
                    .getClass().getClassLoader().getResourceAsStream("db.ini")
            ) {
                String iniContent = readStream( iniStream ) ;
                String[] lines = iniContent.split( "\n" );
                for (String line : lines) {
                    String[] parts = line.split( "=", 2 );
                    if( parts.length == 2 ) {
                        ini.put( parts[0].trim(), parts[1].trim() );
                    }
                }
            }
            catch (IOException ex) {
                System.err.println( ex.getMessage() );
            }

            try {
                mysqlDriver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver( mysqlDriver );
                currenConnection = DriverManager.getConnection(
                        ini.get("connectionString"),
                        ini.get("user"),
                        ini.get("password")
                ) ;
            }
            catch( SQLException ex ) {
                System.err.println( ex.getMessage() );
            }
        }
        return currenConnection;
    }

    private String readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 16];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteBuilder.write(buffer, 0, length);
        }
        return byteBuilder.toString(StandardCharsets.UTF_8.name());
    }

    public void close() {
        try {
            if( currenConnection != null ) {
                currenConnection.close();
            }
            if( mysqlDriver != null ) {
                DriverManager.deregisterDriver( mysqlDriver );
            }
        }
        catch (Exception ignore) {}
    }

}
/*
Інжекція через методи-провайдери -- керована інжекція
 */