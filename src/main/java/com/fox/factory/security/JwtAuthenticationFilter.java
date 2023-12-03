package com.fox.factory.security;


import org.springframework.http.HttpStatus;
import com.fox.factory.entities.User;
import com.fox.factory.service.JwtService;
import com.fox.factory.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    /**
     * Проверяет, что запрос содержит валидный JWT-токен, и если да, то аутентифицирует пользователя.
     * иначе пропускает запрос дальше по цепочке фильтров
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (isBearerAuth(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(7); // убираем префикс "Bearer "
        var username = jwtService.extractUsername(jwt);

        if (canAuthenticate(jwt, username)) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            authenticate(request, userDetails);
        }

        filterChain.doFilter(request, response);
    }

    public User get_user_from_req(@NonNull HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (Objects.equals(authHeader, "") || Objects.equals(authHeader, null) || authHeader.length() <= 7){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        var jwt = authHeader.substring(7); // убираем префикс "Bearer "
        var username = jwtService.extractUsername(jwt);

        if (username != null && getSecurityContext().getAuthentication() != null && jwtService.isNotExpired(jwt)) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            authenticate(request, userDetails);
            return userService.getByUsername(username);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    private boolean canAuthenticate(String jwt, String username) {
        return username != null && getSecurityContext().getAuthentication() == null && jwtService.isNotExpired(jwt);
    }

    private SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    private void authenticate(HttpServletRequest request, UserDetails userDetails) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        getSecurityContext().setAuthentication(authToken);
    }

    private boolean isBearerAuth(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}
