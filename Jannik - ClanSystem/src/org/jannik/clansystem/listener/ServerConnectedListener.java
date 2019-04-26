package org.jannik.clansystem.listener;

import org.jannik.clansystem.ClanSystem;
import org.jannik.clansystem.methods.DatenbankClanMethod;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectedListener
  implements Listener
{
  @EventHandler
  public void onServerConnected(ServerConnectedEvent event)
  {
    final ProxiedPlayer player = event.getPlayer();
    if (player.getServer() == null) {
      BungeeCord.getInstance().getScheduler().runAsync(ClanSystem.getInstance(), new Runnable()
      {
        public void run()
        {
          if (DatenbankClanMethod.isPlayerInClan(player.getUniqueId()))
          {
            final String playerUUID = DatenbankClanMethod.getPlayerUUID(player.getUniqueId());
            if (playerUUID.equals(player.getUniqueId().toString())) {
              DatenbankClanMethod.addPlayerName(player.getName(), player.getUniqueId());
            }
          }
        }
      });
    }
  }
}

