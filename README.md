# gradle

[![GitHub tag](https://img.shields.io/github/tag/xILight/JDBC-API.svg)](https://GitHub.com/xILight/JDBC-API/tags/)

[https://jitpack.io/v/xILight/JDBC-API](https://jitpack.io/v/xILight/JDBC-API)

##To install the library add:

   ```gradle
   repositories { 
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
         implementation 'com.github.xiLight:JDBC-API:4.0'
   }
```

##How to use the api:
   ```java
public static JDBC testDatabase = null;

public static void main(String[] args)
{
        testDatabase = new JDBC(
                "host",
                "database",
                "username",
                "password",
                "port"
        )
        /*
        .addConnectionTimeout(5) //addTimeOut
        .addReconnect(5) //addReconnectAttempts
        .addUnicode("UTF8") //setUnicode
        */
        .queue();


        boolean testDatabaseConnected = testDatabase.isConnected();
        if(testDatabaseConnected)
        System.out.println("TestDataBase connected");
        else
        System.out.println("TestDataBase not connected");
}
```
