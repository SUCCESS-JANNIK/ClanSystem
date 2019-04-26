package org.jannik.clansystem;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jannik.clansystem.commands.CCCommand;
import org.jannik.clansystem.commands.ClanCommand;
import org.jannik.clansystem.listener.PlayerDisconnectListener;
import org.jannik.clansystem.listener.ServerConnectedListener;
import org.jannik.clansystem.manager.FileManager;
import org.jannik.clansystem.methods.DatenbankCreatorMethod;

import net.md_5.bungee.api.plugin.Plugin;

public class ClanSystem extends Plugin {
	
	  private static ClanSystem plugin;
	  private static String prefix = "§eClans §8➤ ";
	  
	  
	  public void onEnable()
	  {
	    plugin = this;
	    
	    getProxy().getPluginManager().registerCommand(plugin, new ClanCommand());
	    getProxy().getPluginManager().registerCommand(plugin, new CCCommand());
	    getProxy().getPluginManager().registerListener(plugin, new ServerConnectedListener());
	    getProxy().getPluginManager().registerListener(plugin, new PlayerDisconnectListener());
	    
	    FileManager.createFile();
	    DatenbankCreatorMethod.readMySQL();
	    DatenbankCreatorMethod.connect();
	    loadDatenbank();
	  }
	  
	  public void onDisable()
	  {
	    DatenbankCreatorMethod.disconnect();
	    plugin = null;
	  }
	  
	  public static ClanSystem getInstance()
	  {
	    return plugin;
	  }
	  
	  public static String getPrefix()
	  {
	    return prefix;
	  }
	  
	  private void loadDatenbank()
	  {
	    
	    try
	    {
	      final PreparedStatement ps = DatenbankCreatorMethod.getConnection().prepareStatement(
	        "CREATE TABLE IF NOT EXISTS ClanManager (PLAYERID int, CLANID int, UUID VARCHAR(64), NAME VARCHAR(64), CLANNAME VARCHAR(64), CLANTAG VARCHAR(64), RANK VARCHAR(64))");
	      ps.executeUpdate();
	    }
	    catch (SQLException e)
	    {
	      System.out.println("[ClanSystem] Ein Fehler beim Erstellen der Datenbank ist aufgetreten.");
	    }
	  }
	}
