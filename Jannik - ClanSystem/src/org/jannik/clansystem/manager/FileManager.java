package org.jannik.clansystem.manager;

import java.io.File;
import java.io.IOException;

import org.jannik.clansystem.ClanSystem;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileManager {
	
	 public static File getFile()
	  {
	    return new File(ClanSystem.getInstance().getDataFolder().getPath(), "config.yml");
	  }
	  
	  public static Configuration getConfiguration()
	  {
	    try
	    {
	      return ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile());
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  public static void createFile()
	  {
	    if (!ClanSystem.getInstance().getDataFolder().exists()) {
	      ClanSystem.getInstance().getDataFolder().mkdir();
	    }
	    File file = getFile();
	    if (!file.exists()) {
	      try
	      {
	        file.createNewFile();
	        Configuration cfg = getConfiguration();
	        cfg.set("mysql.host", "84.200.113.136");
	        cfg.set("mysql.user", "CockyWalk");
	        cfg.set("mysql.datenbase", "CockyWalk");
	        cfg.set("mysql.passwort", "CockyWalk");
	        
	        cfg.set("messages.prefix", "§eClans §8➤ ");
	        cfg.set("messages.noclan", "&cDu bist derzeit in keinem Clan");
	        cfg.set("messages.inclan", "&cDu bist bereits in einem Clan");
	        cfg.set("messages.playernoinclan", "&cDieser Spieler ist nicht in deinem Clan");
	        cfg.set("messages.isinclan", "&cDieser Spieler ist bereits in einem Clan");
	        cfg.set("messages.notonline", "&cDieser Spieler ist derzeit nicht online");
	        cfg.set("messages.notleader", "&cDu musst Leader sein um diesen Command ausführen zu können");
	        cfg.set("messages.create", "&aDu hast den Clan &e[CLANNAME] &8[&e[CLANTAG]&8] &amit der ID &8[&e[CLANID]&8] &aerstellt!");
	        cfg.set("messages.leave", "&cDu hast den Clan &e[CLANNAME] &cverlassen.");
	        cfg.set("messages.delete", "&cDu hast deinen Clan gelöscht");
	        cfg.set("messages.leaderleave", "&cDa du Leader des Clans bist musst du &e/clan delete &cnutzen");
	        cfg.set("messages.playerinvite", "&aDu hast dem Spieler eine Clananfrage gesendet.");
	        cfg.set("messages.targetinvite", "&e[CLANNAME] &ahat dir eine Claneinladung gesendet. Annehmen mit &e/clan accept <Clanname> &aoder &cablehnen mit &e/clan deny &e<Clanname>");
	        cfg.set("messages.playerinvitet", "&cDem Spieler wurde bereits eine Anfrage gesendet.");
	        cfg.set("messages.claninvitet", "&aDu bist dem Clan erfolgreich beigetreten");
	        cfg.set("messages.request", "&cDu hast keine Anfrage von diesem Clan bekommen");
	        cfg.set("messages.kick", "&cDu hast den Spieler &e[PLAYER] &caus dem Clan gekickt");
	        cfg.set("messages.reject", "&cDu hast die Clananfrage &4abgelehnt");
	        cfg.set("messages.clantagexists", "&cDieser Clantag wird derzeit verwendet! Bitte nutze einen anderen!");
	        cfg.set("messages.clannameexists", "&cDieser Clanname wird derzeit verwendet! Bitte nutze einen anderen!");
	        cfg.set("messages.clantaglenght", "&cDer Clantag muss mindestens 3 und maximal 6 Zeichen &clang sein");
	        cfg.set("messages.clannamelenght", "&cDer Clanname muss mindestens 3 und maximal 12 &cZeichen lang sein");
	        cfg.set("messages.noperms", "&cDu hast keine Berechtigung für diesen Befehl.");
	        cfg.set("messages.isrank", "&cDieser Spieler hat bereits diesen Rang");
	        cfg.set("messages.updaterank", "&aDu hast dem Spieler &e[PLAYER] &aden Rang &e[RANK] &agegeben!");
	        cfg.set("messages.rankexists", "&cDieser Rang existiert nicht");
	        cfg.set("messages.listtitle", "&8---------- &6Clanmitglieder &8----------");
	        cfg.set("messages.listfooter", "&8---------- &6Clanmitglieder &8----------");
	        cfg.set("messages.leader", "&4Clanleader&7:");
	        cfg.set("messages.moderator", "&cClanmoderatoren&7:");
	        cfg.set("messages.user", "&aClanmitglieder&7:");
	        cfg.set("messages.listplayers", "&8» &7");
	        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, getFile());
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }
	  
	  public static String getLeader()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.leader");
	  }
	  
	  public static String getModerator()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.moderator");
	  }
	  
	  public static String getUser()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.user");
	  }
	  
	  public static String getPrefix()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.prefix");
	  }
	  
	  public static String createClan(String clanID, String clanName, String clanTag)
	  {
	    Configuration cfg = getConfiguration();
	    String message = cfg.getString("messages.create");
	    message = message.replace("[CLANID]", clanID);
	    message = message.replace("[CLANNAME]", clanName);
	    message = message.replace("[CLANTAG]", clanTag);
	    return message;
	  }
	  
	  public static String updateRank(String playername, String rank)
	  {
	    Configuration cfg = getConfiguration();
	    String message = cfg.getString("messages.updaterank");
	    message = message.replace("[PLAYER]", playername);
	    message = message.replace("[RANK]", rank);
	    return message;
	  }
	  
	  public static String targetInvite(String clanName)
	  {
	    Configuration cfg = getConfiguration();
	    String message = cfg.getString("messages.targetinvite");
	    message = message.replace("[CLANNAME]", clanName);
	    return message;
	  }
	  
	  public static String kick(String playername)
	  {
	    Configuration cfg = getConfiguration();
	    String message = cfg.getString("messages.kick");
	    message = message.replace("[PLAYER]", playername);
	    return cfg.getString("messages.kick");
	  }
	  
	  public static String listFooter()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.listfooter");
	  }
	  
	  public static String rankExists()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.rankexists");
	  }
	  
	  public static String isRank()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.isrank");
	  }
	  
	  public static String noPerms()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.noperms");
	  }
	  
	  public static String clanTagExists()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.clantagexists");
	  }
	  
	  public static String clanNameExists()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.clannameexists");
	  }
	  
	  public static String clanTagLenght()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.clantaglenght");
	  }
	  
	  public static String clanNameLenght()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.clannamelenght");
	  }
	  
	  public static String inClan()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.inclan");
	  }
	  
	  public static String reject()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.reject");
	  }
	  
	  public static String request()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.request");
	  }
	  
	  public static String clanInvitet()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.claninvitet");
	  }
	  
	  public static String playerInvitet()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.playerinvitet");
	  }
	  
	  public static String isInClan()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.isinclan");
	  }
	  
	  public static String playerInvite()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.playerinvite");
	  }
	  
	  public static String listTitle()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.listtitle");
	  }
	  
	  public static String listPlayers()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.listplayers");
	  }
	  
	  public static String leaderLeave()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.leaderleave");
	  }
	  
	  public static String noClan()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.noclan");
	  }
	  
	  public static String playerNoInClan()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.playernoinclan");
	  }
	  
	  public static String notOnline()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.notonline");
	  }
	  
	  public static String notLeader()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.notleader");
	  }
	  
	  public static String leaveClan(String clanName)
	  {
	    Configuration cfg = getConfiguration();
	    String message = cfg.getString("messages.leave");
	    message = message.replace("[CLANNAME]", clanName);
	    return message;
	  }
	  
	  public static String deleteClan()
	  {
	    Configuration cfg = getConfiguration();
	    return cfg.getString("messages.delete");
	  }

}
