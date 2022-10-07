package net.platzhaltergaming.servermanager.util;

import java.util.Map;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerHelper {
  public static boolean serverExists(String name) {
    return (getServerInfo(name) != null);
  }
  
  public static ServerInfo getServerInfo(String name) {
    return ProxyServer.getInstance().getServerInfo(name);
  }
  
  public static void addServer(ServerInfo serverInfo) {
    if (serverExists(serverInfo.getName()))
      return; 
    getServers().put(serverInfo.getName(), serverInfo);
  }
  
  public static void removeServer(String name) {
    if (!serverExists(name))
      return; 
    ServerInfo info = getServerInfo(name);
    for (ProxiedPlayer p : info.getPlayers()) {
      ListenerInfo server = p.getPendingConnection().getListener();
      p.connect(getServersCopy().get((server.getServerPriority().size() > 1) ? server.getServerPriority().get(1) : 
            server.getServerPriority().get(0)));
    } 
    getServers().remove(name);
  }
  
  public static Map<String, ServerInfo> getServersCopy() {
    return ProxyServer.getInstance().getServersCopy();
  }
  
  public static Map<String, ServerInfo> getServers() {
    return ProxyServer.getInstance().getServers();
  }
}
