package net.platzhaltergaming.commonlib.messagessimple;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.config.Configuration;

public class MessagesBungee implements MessagesProvider {

    private Map<String, String> messages;

    public MessagesBungee(Map<String, Object> messages) {
        Map<String, String> scanned = new HashMap<>();
        for (Map.Entry<String, Object> entry : messages.entrySet()) {
            if (entry.getValue() instanceof Configuration) {
                scanMessages(scanned, entry.getKey(), (Configuration) entry.getValue());
            } else {
                scanned.put(entry.getKey(), entry.getValue().toString());
            }
        }

        this.messages = Collections.unmodifiableMap(new LinkedHashMap<String, String>(scanned));
    }

    protected void scanMessages(Map<String, String> scanned, String key, Configuration config) {
        for (String configKey : config.getKeys()) {
            String newKey = key + "." + configKey;
            scanned.put(newKey, config.getString(configKey));
        }
    }

    public String raw(String key) {
        return this.messages.get(key);
    }

    public Component minimessage(String key) {
        return minimessage(key);
    }

    public Component minimessage(String key, TagResolver placeholders) {
        return MiniMessage.miniMessage().deserialize(raw(key), placeholders);
    }

}
