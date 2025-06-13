package com.schedule.eventz.models;

public record LoginRequest(
        String password,
        String email
) {}
