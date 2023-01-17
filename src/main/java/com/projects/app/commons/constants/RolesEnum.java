package com.projects.app.commons.constants;


import java.util.AbstractMap;
import java.util.Map;

public enum RolesEnum {
    ROLE_ADMIN("ROLE_ADMIN",1),
    NORMAL_USER("ROLE_USER",2);

    private final String key;
    private final int value;

    RolesEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getKey() {
        return key;
    }
    public int getValue() {
        return value;
    }
    public Map.Entry<String,Integer> getBoth() {
        return new AbstractMap.SimpleEntry<>(key, value);
    }


}
