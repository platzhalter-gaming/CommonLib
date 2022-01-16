package net.platzhaltergaming.commonlib.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void loadFromResources() throws Exception {
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.US);
        locales.add(Locale.GERMANY);
        Messages messages = new Messages(Paths.get("."), "messages", locales, Locale.US);

        assertEquals("HELLO WORLD!", messages.get(Locale.US, "test123.test"));
        assertEquals("HALLO WELT!", messages.get(Locale.GERMANY, "test123.test"));

        // This should cause the same not found message for both languages
        String key = "notfound404.test";
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.US, key));
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.GERMANY, key));
    }

    @Test
    public void loadFromCustomFiles() throws Exception {
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.US);
        locales.add(Locale.GERMANY);
        Messages messages = new Messages(Paths.get("src/test/resources/messages/loadFromCustomFiles"), "messages", locales, Locale.US);

        assertEquals("HELLO WORLD!", messages.get(Locale.US, "test123.test"));
        assertEquals("HALLO TEST!", messages.get(Locale.GERMANY, "test123.test"));

        // This should cause the same not found message for both languages
        String key = "notfound404.test";
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.US, key));
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.GERMANY, key));
    }

    @Test
    public void fallbackToLanguage() throws Exception {
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.US);
        locales.add(Locale.GERMAN);
        Messages messages = new Messages(Paths.get("src/test/resources/messages/fallbackToLanguage"), "messages", locales, Locale.US);

        assertEquals("HELLO WORLD!", messages.get(Locale.US, "test123.test"));
        // Check that we get the same for any `de*` based language
        assertEquals("HALLO TEST JUST de properties!", messages.get(Locale.GERMANY, "test123.test")); // de_DE
        assertEquals("HALLO TEST JUST de properties!", messages.get(Locale.GERMAN, "test123.test")); // de
        Locale austria = new Locale.Builder().setLanguageTag("de").setRegion("at").build();
        assertEquals("HALLO TEST JUST de properties!", messages.get(austria, "test123.test")); // de_AT

        // This should cause the same not found message for both languages
        String key = "notfound404.test";
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.US, key));
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.GERMANY, key));
        assertEquals(String.format(messages.getNotFoundMessageFormat(), key), messages.get(Locale.GERMAN, key));
    }

}
