package net.platzhaltergaming.commonlib.messagessimple;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public interface MessagesProvider {
    
    public String raw(String key);

    public Component minimessage(String key);

    public Component minimessage(String key, TagResolver placeholders);

}
