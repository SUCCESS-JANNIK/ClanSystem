package org.jannik.clansystem.manager;

import java.util.HashMap;
import java.util.UUID;

public class ClanManager
{
  private static HashMap<UUID, UUID> invites = new HashMap<UUID, UUID>();
  
  public static void addPlayer(UUID target, UUID player)
  {
    if (!containsKey(target)) {
      invites.put(target, player);
    }
  }
  
  public static void removePlayer(UUID uuid)
  {
    if (containsKey(uuid)) {
      invites.remove(uuid);
    }
  }
  
  public static UUID getClan(UUID uuid)
  {
    return (UUID)invites.get(uuid);
  }
  
  public static boolean containsKey(UUID uuid)
  {
    if (invites.containsKey(uuid)) {
      return true;
    }
    return false;
  }
}

