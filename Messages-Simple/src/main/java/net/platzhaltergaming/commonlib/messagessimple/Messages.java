package net.platzhaltergaming.commonlib.messagessimple;

import org.jetbrains.annotations.NotNull;

public class Messages {

    private static MessagesProvider instance;

    public static void register(MessagesProvider instance) {
        Messages.instance = instance;
    }

    public static void unregister() {
        Messages.instance = null;
    }

    @NotNull
    public static MessagesProvider get() {
        if (Messages.instance == null) {
            throw new IllegalStateException("Messages are not loaded yet.");
        }
        return Messages.instance;
    }

}
