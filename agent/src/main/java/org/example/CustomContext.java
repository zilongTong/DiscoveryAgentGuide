package org.example;

import java.util.HashMap;
import java.util.Map;

public class CustomContext {
    private static final ThreadLocal<CustomContext> THREAD_LOCAL = new ThreadLocal<CustomContext>() {
        @Override
        protected CustomContext initialValue() {
            return new CustomContext();
        }
    };

    public static CustomContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private Map<String, String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}