package org.jannik.clansystem.methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jannik.clansystem.manager.FileManager;

import net.md_5.bungee.config.Configuration;

public class DatenbankCreatorMethod
{
  private static String HOST;
  private static String DATABASE;
  private static String USER;
  private static String PASSWORD;
  private static Connection con;
  
  public static void connect()
  {
    if (!isConnected()) {
      try
      {
        con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void disconnect()
  {
    if (isConnected()) {
      try
      {
        con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static boolean isConnected()
  {
    return con != null;
  }
  
  public static Connection getConnection()
  {
    return con;
  }
  
  public static void readMySQL()
  {
    Configuration cfg = FileManager.getConfiguration();
    
    HOST = cfg.getString("mysql.host");
    USER = cfg.getString("mysql.user");
    DATABASE = cfg.getString("mysql.datenbase");
    PASSWORD = cfg.getString("mysql.passwort");
  }
}