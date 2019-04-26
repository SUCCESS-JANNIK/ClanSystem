package org.jannik.clansystem.commands;

import java.util.UUID;

import org.jannik.clansystem.ClanSystem;
import org.jannik.clansystem.manager.FileManager;
import org.jannik.clansystem.methods.DatenbankClanMethod;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CCCommand
  extends Command
{
  private String message = "";
  
  public CCCommand()
  {
    super("cc");
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if ((sender instanceof ProxiedPlayer))
    {
      final ProxiedPlayer player = (ProxiedPlayer)sender;
      final UUID uuid = player.getUniqueId();
      if (player.hasPermission("clan.use"))
      {
        if (args.length >= 1) {
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
                      target.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + "§8" + player.getName() + "§7: §e" + CCCommand.this.message));
                    }
                  }
                }
                CCCommand.this.message = "";
              }
            });
          }
          else
          {
            player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noClan())));
          }
        }
      }
      else {
        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', FileManager.getPrefix()) + " " + ChatColor.translateAlternateColorCodes('&', FileManager.noPerms())));
      }
    }
  }
}