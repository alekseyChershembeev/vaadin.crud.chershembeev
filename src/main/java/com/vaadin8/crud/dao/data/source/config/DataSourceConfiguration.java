package com.vaadin8.crud.dao.data.source.config;



import org.apache.commons.dbcp.BasicDataSource;


import java.util.logging.Logger;

public class DataSourceConfiguration {

    public DataSourceConfiguration() {

        logger.info("databaseConnection");
    }


    private static BasicDataSource dataSource;

    private static Logger logger = Logger.getLogger(DataSourceConfiguration.class.getName());

    private static BasicDataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(System.getProperty("driver.class.name"));
        dataSource.setUrl(System.getProperty("url"));
        dataSource.setUsername(System.getProperty("username"));
        dataSource.setPassword(System.getProperty("password"));


        return dataSource;
    }
    /**
    Соеденинение с БД всегда в 1 экземпляре
    **/
    public static synchronized BasicDataSource getInstance() {
        if (dataSource == null) {
            dataSource = dataSource();
            return dataSource;
        }
        return dataSource;
    }
}



