package org.jannik.clansystem.commands;

import java.util.HashMap;
import java.util.UUID;

import org.jannik.clansystem.ClanSystem;
import org.jannik.clansystem.manager.ClanManager;
import org.jannik.clansystem.manager.FileManager;
import org.jannik.clansystem.methods.DatenbankClanMethod;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ClanCommand
  extends Command
{
  private HashMap<String, String> playersInClan = new HashMap<>();
  private String message = "";
  
  public ClanCommand()
  {
    super("clan");
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if ((sender instanceof ProxiedPlayer))
    {
      final ProxiedPlayer player = (ProxiedPlayer)sender;
      final UUID uuid = player.getUniqueId();
      if (player.hasPermission("clan.use"))
      {
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("leave"))
          {
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              if (!DatenbankClanMethod.isClanLeader(uuid).equals("leader"))
              {
                int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                String clanname = DatenbankClanMethod.getClanName(clanID);
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.leaveClan(clanname))));
                DatenbankClanMethod.removePlayer(uuid);
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.leaderLeave())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("delete"))
          {
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              if (DatenbankClanMethod.isClanLeader(uuid).equals("leader"))
              {
                int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                int playerIDs = DatenbankClanMethod.getPlayerID();
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.deleteClan())));
                for (int i = 1; i <= playerIDs; i++)
                {
                  String playername = DatenbankClanMethod.getPlayerID(i);
                  if (DatenbankClanMethod.getClanID(playername).equals(Integer.valueOf(clanID))) {
                    DatenbankClanMethod.removePlayer(playername);
                  }
                }
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notLeader())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("list"))
          {
            if (DatenbankClanMethod.isPlayerInClan(uuid)) {
              BungeeCord.getInstance().getScheduler().runAsync(ClanSystem.getInstance(), new Runnable()
              {
                public void run()
                {
                  int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                  int playerIDs = DatenbankClanMethod.getPlayerID();
                  String playername;
                  for (int i = 1; i <= playerIDs; i++)
                  {
                    playername = DatenbankClanMethod.getPlayerID(i);
                    if (DatenbankClanMethod.getClanID(playername).equals(Integer.valueOf(clanID)))
                    {
                      String rank = DatenbankClanMethod.isClanLeader(playername);
                      ClanCommand.this.playersInClan.put(playername, rank);
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listTitle())));
                  player.sendMessage(new TextComponent(""));
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getLeader())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("leader")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getModerator())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("moderator")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getUser())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("user")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(""));
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listFooter())));
                  ClanCommand.this.playersInClan.clear();
                }
              });
            } else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("info")) {
            if (DatenbankClanMethod.isPlayerInClan(uuid)) {
              BungeeCord.getInstance().getScheduler().runAsync(ClanSystem.getInstance(), new Runnable()
              {
                public void run()
                {
                  int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                  int playerIDs = DatenbankClanMethod.getPlayerID();
                  String playername;
                  for (int i = 1; i <= playerIDs; i++)
                  {
                    playername = DatenbankClanMethod.getPlayerID(i);
                    if (DatenbankClanMethod.getClanID(playername).equals(Integer.valueOf(clanID)))
                    {
                      String rank = DatenbankClanMethod.isClanLeader(playername);
                      ClanCommand.this.playersInClan.put(playername, rank);
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listTitle())));
                  player.sendMessage(new TextComponent(""));
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getLeader())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("leader")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getModerator())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("moderator")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getUser())));
                  for (String pname : ClanCommand.this.playersInClan.keySet()) {
                    if (((String)ClanCommand.this.playersInClan.get(pname)).equals("user")) {
                      if (BungeeCord.getInstance().getPlayer(pname) != null) {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§aOnline§7)"));
                      } else {
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listPlayers()) + pname + " §7(§cOffline§7)"));
                      }
                    }
                  }
                  player.sendMessage(new TextComponent(""));
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.listFooter())));
                  ClanCommand.this.playersInClan.clear();
                }
              });
            } else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
          }
        }
        else if (args.length == 2)
        {
          if (args[0].equalsIgnoreCase("invite"))
          {
            ProxiedPlayer target = ClanSystem.getInstance().getProxy().getPlayer(args[1]);
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              if (target != null)
              {
                if (DatenbankClanMethod.isClanLeader(uuid).equals("leader"))
                {
                  if (!DatenbankClanMethod.isPlayerInClan(target.getUniqueId()))
                  {
                    if (!ClanManager.containsKey(uuid))
                    {
                      int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                      String clanname = DatenbankClanMethod.getClanName(clanID);
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.playerInvite())));
                      target.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.targetInvite(clanname))));
                      ClanManager.addPlayer(target.getUniqueId(), player.getUniqueId());
                    }
                    else
                    {
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.playerInvitet())));
                    }
                  }
                  else {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.isInClan())));
                  }
                }
                else {
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notLeader())));
                }
              }
              else {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notOnline())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("accept"))
          {
            if (ClanManager.containsKey(uuid))
            {
              String name = args[1];
              int clanID = DatenbankClanMethod.getClanID(ClanManager.getClan(uuid)).intValue();
              String clanname = DatenbankClanMethod.getClanName(clanID);
              if (name.equals(clanname))
              {
                String clantag = DatenbankClanMethod.getClanTag(clanID);
                int playerID = DatenbankClanMethod.getPlayerID();
                playerID++;
                DatenbankClanMethod.addPlayer(playerID, clanID, uuid, player.getName(), clanname, clantag, "user");
                ClanManager.removePlayer(uuid);
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.clanInvitet())));
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.request())));
              }
            }
            else
            {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.request())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("deny"))
          {
            if (ClanManager.containsKey(uuid))
            {
              String name = args[1];
              int clanID = DatenbankClanMethod.getClanID(ClanManager.getClan(uuid)).intValue();
              String clanname = DatenbankClanMethod.getClanName(clanID);
              if (name.equals(clanname))
              {
                DatenbankClanMethod.removePlayer(uuid);
                ClanManager.removePlayer(uuid);
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.reject())));
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.request())));
              }
            }
            else
            {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.request())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("kick")) {
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              if (DatenbankClanMethod.isClanLeader(uuid).equals("leader"))
              {
                String playername = args[1];
                if (DatenbankClanMethod.isPlayerInClan(playername))
                {
                  int playerClanID = DatenbankClanMethod.getClanID(uuid).intValue();
                  int targetClanID = DatenbankClanMethod.getClanID(playername).intValue();
                  if (playerClanID == targetClanID)
                  {
                    DatenbankClanMethod.removePlayer(playername);
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.kick(playername))));
                  }
                  else
                  {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.playerNoInClan())));
                  }
                }
                else
                {
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.playerNoInClan())));
                }
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notLeader())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
          }
        }
        else if ((args.length >= 2) && 
          (args[0].equalsIgnoreCase("msg")))
        {
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              for (int i = 0; i < args.length; i++) {
                this.message = (this.message + args[i] + " ");
              }
              BungeeCord.getInstance().getScheduler().runAsync(ClanSystem.getInstance(), new Runnable()
              {
                public void run()
                {
                  int clanID = DatenbankClanMethod.getClanID(uuid).intValue();
                  int playerIDs = DatenbankClanMethod.getPlayerID();
                  for (int i = 1; i <= playerIDs; i++)
                  {
                    String playername = DatenbankClanMethod.getPlayerID(i);
                    if (DatenbankClanMethod.getClanID(playername).equals(Integer.valueOf(clanID)))
                    {
                      final ProxiedPlayer target = BungeeCord.getInstance().getPlayer(playername);
                      if (target != null) {
                        target.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + "§8" + player.getName() + "§7: §e" + ClanCommand.this.message));
                      }
                    }
                  }
                  ClanCommand.this.message = "";
                }
              });
            }
        }
        if ((args.length == 3))
        {
          if (args[0].equalsIgnoreCase("create"))
          {
            String clanname = args[1];
            String clantag = args[2];
            if ((clanname.length() > 2) && (clanname.length() < 12))
            {
              if ((clantag.length() > 2) && (clantag.length() < 6))
              {
                if (!DatenbankClanMethod.isPlayerInClan(uuid))
                {
                  if (!DatenbankClanMethod.isClanNameExtists(clanname))
                  {
                    if (!DatenbankClanMethod.isClanTagExtists(clantag))
                    {
                      int clanID = DatenbankClanMethod.getClanIDs().intValue();
                      int playerID = DatenbankClanMethod.getPlayerID();
                      clanID++;
                      playerID++;
                      DatenbankClanMethod.addPlayer(playerID, clanID, uuid, player.getName(), clanname, clantag, "leader");
                      String toClanID = Integer.toString(clanID);
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.createClan(toClanID, clanname, clantag))));
                    }
                    else
                    {
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.clanTagExists())));
                    }
                  }
                  else {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.clanNameExists())));
                  }
                }
                else {
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.inClan())));
                }
              }
              else {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.clanTagLenght())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.clanNameLenght())));
            }
            return;
          }
          if (args[0].equalsIgnoreCase("promote"))
          {
            if (DatenbankClanMethod.isPlayerInClan(uuid))
            {
              if (DatenbankClanMethod.isClanLeader(uuid).equals("leader"))
              {
                ProxiedPlayer target = ClanSystem.getInstance().getProxy().getPlayer(args[1]);
                if (target != null)
                {
                  String rank = args[2];
                  if ((rank.equals("leader")) || 
                    (rank.equals("moderator")) || 
                    (rank.equals("user")))
                  {
                    if (!DatenbankClanMethod.isClanLeader(target.getUniqueId()).equals(rank.toLowerCase()))
                    {
                      DatenbankClanMethod.updateClanRank(target.getUniqueId(), rank.toLowerCase());
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.updateRank(target.getName(), rank))));
                    }
                    else
                    {
                      player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.isRank())));
                    }
                  }
                  else {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.rankExists())));
                  }
                }
                else
                {
                  player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notOnline())));
                }
              }
              else
              {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.notLeader())));
              }
            }
            else {
              player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
            }
            return;
          }
        }
        sendHelp(player);
      }
      else
      {
        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noPerms())));
      }
    }
  }
  
  private void sendHelp(ProxiedPlayer player)
  {
    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + "§7Informationen zur §6Clan Verwaltung"));
    player.sendMessage(new TextComponent("§a/clan create <Name> <Tag>  §7Erstelle einen neuen Clan"));
    player.sendMessage(new TextComponent("§a/clan invite <Spielername> §7Lade einen Spieler in deinen Clan ein"));
    player.sendMessage(new TextComponent("§a/clan list §7Listet alle Clan Mitglieder auf"));
    player.sendMessage(new TextComponent("§a/clan info §7Listet alle Clan Mitglieder auf"));
    player.sendMessage(new TextComponent("§a/clan accept <Clanname> §7Nimm eine Anfrage an"));
    player.sendMessage(new TextComponent("§a/clan deny <Clanname> §7Lehne eine Anfrage ab"));
    player.sendMessage(new TextComponent("§a/clan kick <Spielername> §7Kicke einen Spieler aus deinem Clan"));
    player.sendMessage(new TextComponent("§a/clan leave §7Verlasse einen Clan"));
    player.sendMessage(new TextComponent("§a/clan delete §7Lösche einen Clan"));
    player.sendMessage(new TextComponent("§a/clan msg <Nachricht> §7Sende eine Clan Message an alle Clan §7Spieler"));
    player.sendMessage(new TextComponent("§a/cc <Nachricht> §7Sende eine Clan Message an alle Clan Spieler"));
    player.sendMessage(new TextComponent("§a/clan promote <Spielername> <user, moderator, leader> §7Promote §7einen Spieler"));
  }
}
