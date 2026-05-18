package com.schefer.agenda.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Em produção, mova essa chave para uma variável de ambiente
    @Value("${api.security.token.secret}")
    private String SECRET;
    private static final long EXPIRACAO_MS = 1000 * 60 * 60 * 8; // 8 horas

    private SecretKey getChave() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /** Gera um token JWT com o ID do usuário e sua role */
    public String gerarToken(Long id, String role) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACAO_MS))
                .signWith(getChave())
                .compact();
    }

    /** Extrai o ID do usuário contido no token */
    public Long extrairId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    /** Extrai a role contida no token */
    public String extrairRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /** Valida se o token é legítimo e não está expirado */
    public boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
