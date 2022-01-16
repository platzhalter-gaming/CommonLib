package net.platzhaltergaming.commonlib.messages;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import com.ibm.icu.text.MessageFormat;

import lombok.Getter;
import lombok.Setter;

public class Messages implements MessagesInterface {

    @Getter
    @Setter
    private String notFoundMessageFormat = "";

    private final Locale fallbackLocale;

    private Map<Locale, ResourceBundle> bundles = new ConcurrentHashMap<>();

    public Messages(Path dir, String basename, List<Locale> locales) throws MissingResourceException, RuntimeException {
        this(dir, basename, locales, Locale.getDefault());
    }

    public Messages(Path dir, String basename, List<Locale> locales, Locale fallback)
            throws MissingResourceException, RuntimeException {
        this.fallbackLocale = fallback;

        for (Locale locale : locales) {
            String fileName = String.format("%s_%s.properties", basename, locale.toString());
            ResourceBundle bundle;

            File file = new File(dir.toString(), fileName);
            if (file.isFile()) {
                try (FileReader rd = new FileReader(file)) {
                    bundle = new PropertyResourceBundle(rd);
                } catch (IOException exc) {
                    throw new RuntimeException(String.format("Could not load file based %s", fileName), exc);
                }
            } else {
                bundle = ResourceBundle.getBundle(basename, locale);
            }
            bundles.put(locale, bundle);
        }

        if (!bundles.containsKey(fallbackLocale)) {
            throw new RuntimeException(String.format("Fallback Locale %s not found", fallbackLocale));
        }
    }

    public String get(Locale locale, String key) {
        return get(locale, key, null);
    }

    public String get(Locale locale, String key, Object data) {
        MessageFormat formatter = new MessageFormat(getResourceKey(locale, key), locale);
        return formatter.format(data);
    }

    protected String getResourceKey(Locale locale, String key) {
        try {
            return getResourceBundle(locale).getString(key);
        } catch (MissingResourceException exc) {
            try {
                return getFallbackBundle().getString(key);
            } catch (MissingResourceException exc2) {
                return String.format(notFoundMessageFormat, key);
            }
        }
    }

    protected ResourceBundle getFallbackBundle() {
        return bundles.get(fallbackLocale);
    }

    protected ResourceBundle getResourceBundle(Locale locale) {
        // If the locale is null or the locale doesn't exist, return the fallback locale
        if (locale == null) {
            return getFallbackBundle();
        }

        if (bundles.containsKey(locale)) {
            return bundles.get(locale);
        } else {
            // Check if the bundle exists but with just the language set (not
            // country/region)
            Locale lang = new Locale.Builder().setLanguage(locale.getLanguage()).build();
            if (bundles.containsKey(lang)) {
                return bundles.get(lang);
            } else {
                return getFallbackBundle();
            }
        }
    }

    public NamespacedMessages getNamespaced(String namespace) {
        return new NamespacedMessages(this, namespace);
    }

    public List<Locale> getLocales() {
        return new ArrayList<>(bundles.keySet());
    }

}
