package by.it_academy.jd2.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private static final String envConfigUrl = System.getenv("DATABASE_URL");
    private static final String envConfigUsername = System.getenv("DATABASE_USERNAME");
    private static final String envConfigPassword = System.getenv("DATABASE_PASSWORD");

    private static DataSource dataSource;

    private ConnectionManager() {}

    static {
        load();
    }

    /**
     * Для получения доступа к базе данных используются параметры (URL, username, password)
     * из файла /resources/application.properties. Для изменения параметров воспользуйтесь
     * переменными среды DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD.
     */
    private static void load() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();

        try {
            cpds.setDriverClass("org.postgresql.Driver");
        }  catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        cpds.setJdbcUrl(getUrl());
        cpds.setUser(getUsername());
        cpds.setPassword(getPassword());

        dataSource = cpds;

    }

    public static Connection open() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения",e);
        }
    }

    private static String getUrl() {
        return isValidEnvVariables() ? envConfigUrl : PropertiesUtil.get(URL_KEY);
    }

    private static String getUsername() {
        return isValidEnvVariables() ? envConfigUsername : PropertiesUtil.get(USERNAME_KEY);
    }

    private static String getPassword() {
        return isValidEnvVariables() ? envConfigPassword : PropertiesUtil.get(PASSWORD_KEY);
    }

    private static boolean isValidEnvVariables() {
        return envConfigUrl != null && envConfigUsername != null && envConfigPassword != null;
    }

}
