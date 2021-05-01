package de.jens98.utils.exceptions;

public class JdbcCantConnectException extends Exception
{
    /*
    Created by Jens Simbrey - 23.04.2021 12:00Uhr - @Copyright 2021
     */
    public JdbcCantConnectException(String errorMessage, Throwable throwable)
    {
        super(errorMessage, throwable);
    }
}
