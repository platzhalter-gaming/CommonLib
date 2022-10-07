package net.platzhaltergaming.commonlib.messages;

import java.util.Locale;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NamespacedMessages implements MessagesInterface {

    private final Messages messages;
    private final String namespace;

    public String get(Locale locale, String key, Object data) {
        return messages.get(locale, namespace + "." + key, data);
    }

    public String get(Locale locale, String key) {
        return messages.get(locale, key, null);
    }

}
