package com.example.cuoiki.Model;

public class KeyValueCategories {
    private String key;
    private String value;

    public KeyValueCategories(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
