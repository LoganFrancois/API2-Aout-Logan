package myconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.PropertyResourceBundle;

public class ODBCConnection {
    protected String dbName;
    protected String username;
    protected String password;
    public ODBCConnection() {
        PropertyResourceBundle properties = (PropertyResourceBundle)
                PropertyResourceBundle.getBundle("resources.application");
        dbName =properties.getString("cours.DB.database");
        username=properties.getString("cours.DB.login");
        password=properties.getString("cours.DB.password");

    }

    public ODBCConnection(String username,String password){
        this();
        this.username=username;
        this.password=password;
    }


    public Connection getConnection() {
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //chargement du driver JDBC-ODBC

            dbName="SGBD4-Q2";
            String url = "jdbc:odbc:"+dbName;
            return DriverManager.getConnection(url,username,password);
        }
        catch(Exception e) {
            System.out.println("erreur de connexion "+e);
            e.printStackTrace();
            return null ;
        }
    }
}