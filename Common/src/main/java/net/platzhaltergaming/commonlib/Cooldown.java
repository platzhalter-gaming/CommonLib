package net.platzhaltergaming.commonlib;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cooldown<T> {

    private ConcurrentHashMap<T, Long> entries = new ConcurrentHashMap<T, Long>();

    private final long cooldown;

    public long check(T key) {
        if (entries.containsKey(key)) {
            long milliSecondsLeft = (entries.get(key) + cooldown) - System.currentTimeMillis();
            if (milliSecondsLeft > 0) {
                return milliSecondsLeft / 1000;
            }
        }

        remove(key);
        return 0;
    }

    public long check(T key, long leeway) {
        long left = check(key);

        // If the time left is in the leeway, we are good to go
        // remove the key
        if (left <= leeway) {
            this.remove(key);
            return 0;
        }
        return left;
    }

    public boolean has(T key) {
        return (check(key) > 0);
    }

    public void add(T key) {
        entries.put(key, System.currentTimeMillis());
    }

    public void remove(T key) {
        entries.remove(key);
    }

    public Enumeration<T> keys() {
        return entries.keys();
    }

}
