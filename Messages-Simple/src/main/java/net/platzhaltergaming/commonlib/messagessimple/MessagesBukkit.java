package net.platzhaltergaming.commonlib.messagessimple;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.MemorySection;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MessagesBukkit implements MessagesProvider {
    
    private Map<String, String> messages;

    public MessagesBukkit(Map<String, Object> messages) {
        Map<String, String> scanned = new HashMap<>();
        for (Map.Entry<String, Object> entry : messages.entrySet()) {
            if (entry.getValue() instanceof MemorySection) {
                scanMessages(scanned, entry.getKey(), (MemorySection) entry.getValue());
            } else {
                scanned.put(entry.getKey(), entry.getValue().toString());
            }
        }

        this.messages = Collections.unmodifiableMap(new LinkedHashMap<String, String>(scanned));
    }

    protected void scanMessages(Map<String, String> scanned, String key, MemorySection config) {
        for (Map.Entry<String, Object> entry : config.getValues(true).entrySet()) {
            String newKey = key + "." + entry.getKey();
            if (entry.getValue() instanceof MemorySection) {
                scanMessages(scanned, newKey, (MemorySection) entry.getValue());
            } else {
                scanned.put(newKey, entry.getValue().toString());
            }
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
