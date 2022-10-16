package net.platzhaltergaming.servermanager.api;

import java.net.SocketAddress;
import java.util.Collection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;
import net.platzhaltergaming.servermanager.api.events.ServerAddEvent;
import net.platzhaltergaming.servermanager.api.events.ServerRemoveEvent;
import net.platzhaltergaming.servermanager.util.ServerHelper;

public class ServerManagerAPI {
    private final Plugin plugin;

    public ServerManagerAPI(Plugin plugin) {
        this.plugin = plugin;
    }

    private Plugin getPlugin() {
        return this.plugin;
    }

    public boolean addServer(String name, SocketAddress ipAddress, String motd, boolean restricted) {
        if (ServerHelper.getServerInfo(name) != null)
            return false;
        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, ipAddress, motd, restricted);
        ServerAddEvent addEvent = new ServerAddEvent(serverInfo, getPlugin().getProxy().getConsole());
        getPlugin().getProxy().getPluginManager().callEvent((Event) addEvent);
        if (addEvent.isCancelled())
            return false;
        ServerHelper.addServer(addEvent.getServerModified());
        return true;
    }

    public boolean removeServer(String name) {
        ServerInfo serverInfo = ServerHelper.getServerInfo(name);
        if (serverInfo == null)
            return false;
        ServerRemoveEvent removeEvent = new ServerRemoveEvent(serverInfo, getPlugin().getProxy().getConsole());
        getPlugin().getProxy().getPluginManager().callEvent((Event) removeEvent);
        if (removeEvent.isCancelled())
            return false;
        ServerHelper.removeServer(removeEvent.getServerModified().getName());
        return true;
    }

    public ServerInfo getServerInfo(String name) {
        return ServerHelper.getServerInfo(name);
    }

    public boolean serverExists(String name) {
        return ServerHelper.serverExists(name);
    }

    public Collection<ServerInfo> listServers() {
        return ServerHelper.getServers().values();
    }
}
