package s1014ftjavaangular.security.infrastructure.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import s1014ftjavaangular.security.domain.service.JwtProvider;
import s1014ftjavaangular.security.domain.utils.SecurityUtils;
import s1014ftjavaangular.security.infrastructure.security.AccountPrincipal;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService accountDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Recupera el token del header
        final String authorizationHeader = request.getHeader("Authorization");
        //Si el header esta vacio no realiza el filtro
        if (!StringUtils.hasText(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = SecurityUtils.cleanBearer(authorizationHeader);
        final String email = jwtProvider.extractSubject(token);

        AccountPrincipal account =
                (email != null)
                        ? (AccountPrincipal) this.accountDetailsService.loadUserByUsername(email)
                        : AccountPrincipal.builder().build();

        if (
                jwtProvider.isTokenValid(token, account) &&
                        SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            //UsernamePasswordAuthenticationToken es una instancia de Authentication
            var authUser = new UsernamePasswordAuthenticationToken(
                    account,
                    account.getPassword(),
                    account.getAuthorities()
            );

            authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authUser);
        }

        filterChain.doFilter(request, response);
    }
}
