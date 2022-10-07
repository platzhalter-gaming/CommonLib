package net.platzhaltergaming.servermanager.api.events;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Event;

public abstract class ServerEvent extends Event {
  private ServerInfo serverModified;
  
  public ServerEvent(ServerInfo serverModified) {
    this.serverModified = serverModified;
  }
  
  public ServerInfo getServerModified() {
    return this.serverModified;
  }
}
