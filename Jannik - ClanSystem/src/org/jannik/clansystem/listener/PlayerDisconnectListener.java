package org.jannik.clansystem.listener;

import org.jannik.clansystem.manager.ClanManager;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener
  implements Listener
{
  @EventHandler
  public void onPlayerDisconnect(PlayerDisconnectEvent event)
  {
    final ProxiedPlayer player = event.getPlayer();
    if ((player != null) && 
      (ClanManager.containsKey(player.getUniqueId()))) {
      ClanManager.removePlayer(player.getUniqueId());
    }
  }
}