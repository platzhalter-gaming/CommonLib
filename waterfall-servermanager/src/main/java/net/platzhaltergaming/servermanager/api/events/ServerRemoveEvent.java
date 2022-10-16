package net.platzhaltergaming.servermanager.api.events;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Cancellable;

public class ServerRemoveEvent extends ServerEvent implements Cancellable {
    private CommandSender sender;

    private boolean cancelled;

    public ServerRemoveEvent(ServerInfo serverModified, CommandSender sender) {
        super(serverModified);
        this.sender = sender;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
