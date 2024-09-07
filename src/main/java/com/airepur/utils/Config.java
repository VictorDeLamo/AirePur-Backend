package com.airepur.utils;

public class Config {

    public final static String URL = System.getenv("DB_URL");
    public final static String username = System.getenv("DB_USERNAME");
    public final static String password = System.getenv("DB_PASSWORD");
    public final static String appPwd = System.getenv("APP_PASSWORD");
}