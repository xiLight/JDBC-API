package de.jens98.utils;

import de.jens98.utils.exceptions.JdbcCantConnectException;
import de.jens98.utils.interfaces.DatabaseJDBC;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class JDBC implements DatabaseJDBC
{
    /*
    Created by Jens Simbrey - 23.04.2021 12:00Uhr - @Copyright 2021
     */

    @Getter
    private String jbdcConnectString = "";

    @Getter
    private boolean connected;
    @Getter
    private boolean autoReconnect;
    @Getter
    private int maxReconnects = 5;
    @Getter
    private boolean useConnectionTimeout;
    @Getter
    private int connectionTimeout;
    @Getter
    private boolean useCharacterEncoding;
    @Getter
    private String characterEncoding;

    @Getter
    private String host;
    @Getter
    private String database;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String port;

    @Getter
    private Connection connection;
    @Getter
    private Statement statement;

    @Getter
    private Properties properties;

    public JDBC(String host, String database, String username, String password, String port)
    {
        this.properties = new Properties();
        this.connection = null;
        this.statement = null;
        this.connected = false;
        this.autoReconnect = false;
        this.useConnectionTimeout = false;
        this.useCharacterEncoding = false;
        this.connectionTimeout = 0;

        this.jbdcConnectString += jbdcConnect + host + ":" + port + "/" + database;
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;

        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
        this.properties.setProperty("useInformationSchema", "true");
        this.properties.setProperty("nullCatalogMeansCurrent", "false");
        this.properties.setProperty("tinyInt1isBit", "false");
    }

    public JDBC addReconnect(int maxReconnects)
    {
        this.autoReconnect = true;
        this.maxReconnects = maxReconnects;
        return this;
    }

    public JDBC addUnicode(String encoding)
    {
        this.useCharacterEncoding = true;
        this.characterEncoding = encoding;
        return this;
    }

    public JDBC addConnectionTimeout(int connectionTimeout)
    {
        this.useConnectionTimeout = true;
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public JDBC queue()
    {
        CompletableFuture.runAsync(() ->
        {
            if(this.useCharacterEncoding)
            {
                this.properties.setProperty("useUnicode", "true");
                this.properties.setProperty("characterEncoding", "utf8");
            }
            if (this.autoReconnect)
            {
                this.properties.setProperty("autoReconnect", "true");
                this.properties.setProperty("maxReconnects", String.valueOf(this.maxReconnects));
            }
            if (this.useConnectionTimeout)
            {
                this.properties.setProperty("connectTimeout", String.valueOf(this.connectionTimeout));
            }

            try
            {
                this.connection = DriverManager.getConnection(this.jbdcConnectString, this.properties);
                this.connected = true;
            } catch (SQLException sqlException)
            {
                try
                {
                    throw new JdbcCantConnectException("Database can't connect!", sqlException.getCause());
                } catch (JdbcCantConnectException e)
                {
                    e.printStackTrace();
                }
            }
        });
        return this;
    }

}
