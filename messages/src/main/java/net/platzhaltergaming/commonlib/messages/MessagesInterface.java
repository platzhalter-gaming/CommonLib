package net.platzhaltergaming.commonlib.messages;

import java.util.Locale;

public interface MessagesInterface {

    public String get(Locale locale, String key, Object data);

    public String get(Locale locale, String key);

}
