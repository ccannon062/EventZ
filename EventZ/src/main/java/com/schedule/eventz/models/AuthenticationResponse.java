package com.schedule.eventz.models;

public record AuthenticationResponse(
        String token,
        User user,
        Long expiresIn
) {}
