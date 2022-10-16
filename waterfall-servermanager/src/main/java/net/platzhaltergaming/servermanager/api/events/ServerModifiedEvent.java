package net.platzhaltergaming.servermanager.api.events;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Cancellable;

public class ServerModifiedEvent<T> extends ServerEvent implements Cancellable {
    private CommandSender sender;

    private ServerField modified;

    private T newValue;

    private boolean cancelled;

    public ServerModifiedEvent(ServerInfo serverModified, CommandSender sender, ServerField modified, T newValue) {
        super(serverModified);
        this.sender = sender;
        this.modified = modified;
        this.newValue = newValue;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public ServerField getModified() {
        return this.modified;
    }

    public T getNewValue() {
        return this.newValue;
    }

    public void setNewValue(T newValue) {
        this.newValue = newValue;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum ServerField {
        NAME, IP, MOTD, RESTRICTED;
    }
}
