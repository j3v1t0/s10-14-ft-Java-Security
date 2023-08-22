package s1014ftjavaangular.security.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import s1014ftjavaangular.security.domain.service.JwtProvider;
import s1014ftjavaangular.security.infrastructure.security.AccountPrincipal;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtProviderImpl implements JwtProvider<AccountPrincipal> {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    @Value("${app.jwt.expiration-in-minute}")
    private String JWT_EXPIRATION;

    @Override
    public String generateToken(AccountPrincipal userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    @Override
    public String generateToken(AccountPrincipal userDetails, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("id", userDetails.getId())
                .claim("authority", userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(JWT_EXPIRATION))))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public Boolean hasClaim(String token, String name) {
        return this.extractAllClaims(token).get(name) != null;
    }

    @Override
    public String extractSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }


    @Override
    public Date extractExpired(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }


    @Override
    public Boolean isTokenValid(String token, AccountPrincipal userDetails) {
        //El token no debe de estar vacio
        Assert.isTrue(StringUtils.hasText(token), "The token is empty, token is invalid");

        //Recupera el email
        final String acountEmail = this.extractSubject(token);

        //Verifica que el usuario no sea nulo
        Assert.notNull(userDetails, "The user details is null, token is invalid");

        //Verifica que el email del token no este vacio
        Assert.isTrue(StringUtils.hasText(acountEmail), "The token is empty, token is invalid");

        //Verifica que el email del token y del usuario sean iguales
        Assert.isTrue(acountEmail.equals(userDetails.getUsername()), "Email does match, token is invalid");

        //Finalmene que el token no haya caducado
        return !isTokenExpired(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return this.extractExpired(token).before(new Date());
    }

    @Override
    public <S> S extractClaim(String token, String name, Class<S> type) {
        if (!this.hasClaim(token, name)) return null;
        var claims = this.extractAllClaims(token);

        return type.cast(claims.get(name));
    }

    /**
     * Recupera una claim que este por default en el token
     *
     * @param token    Token JWT
     * @param resolver Funcion con el tipo de dato y el metodo para extraer la claim
     * @param <T>      Cambia segun el tipo de dato de la claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Recupera todas las claims del token
     *
     * @param token Token JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Recupera la llave de cifrado del token
     */
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
