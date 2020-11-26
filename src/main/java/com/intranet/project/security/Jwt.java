package com.intranet.project.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class Jwt {

    public String getBearerToken() {
        JwtBuilder builder = Jwts.builder()
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setIssuer("siiri")
                .signWith(SignatureAlgorithm.HS256, "secret");
        String jwt = builder.compact();
        return jwt;
    }
}
