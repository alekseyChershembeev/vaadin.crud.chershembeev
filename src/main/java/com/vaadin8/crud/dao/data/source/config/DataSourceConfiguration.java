package com.vaadin8.crud.dao.data.source.config;


import org.apache.commons.dbcp.BasicDataSource;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Logger;

public class DataSourceConfiguration {

    public DataSourceConfiguration() {

        logger.info("databaseConnection");
    }


    private static BasicDataSource dataSource;

    private static Logger logger = Logger.getLogger(DataSourceConfiguration.class.getName());

    private static BasicDataSource dataSource() {

        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            BasicDataSource dataSource = new BasicDataSource();
//
                dataSource.setConnectionInitSqls(Collections.singleton(getConnection()));
            } catch (SQLException | URISyntaxException e) {
                e.printStackTrace();
            }



//        dataSource.setDriverClassName(System.getProperty("driver.class.name"));
//        dataSource.setUrl(System.getProperty("url"));
//        dataSource.setUsername(System.getProperty("username"));
//        dataSource.setPassword(System.getProperty("password"));


        return dataSource;
    }

    /**
     * Соеденинение с БД всегда в 1 экземпляре
     **/
    public static synchronized BasicDataSource getInstance() {
        if (dataSource == null) {
            dataSource = dataSource();
            return dataSource;
        }
        return dataSource;
    }

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }


}



