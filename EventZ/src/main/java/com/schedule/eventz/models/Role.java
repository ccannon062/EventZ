package com.schedule.eventz.models;

public enum Role {
    USER,
    ADMIN,
    EVENT_ORGANIZER,;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
