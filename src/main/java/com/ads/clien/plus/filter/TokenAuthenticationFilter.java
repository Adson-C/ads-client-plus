package com.ads.clien.plus.filter;

import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.model.jpa.UserCredentials;
import com.ads.clien.plus.repository.jpa.UserDetailsRepository;
import com.ads.clien.plus.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenService tokenService;

    private UserDetailsRepository userDetailsRepository;

    public TokenAuthenticationFilter(TokenService tokenService, UserDetailsRepository userDetailsRepository) {
        this.tokenService = tokenService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);
        if (tokenService.isValid(token)) {
            authByToken(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authByToken(String token) {

        //recuperar id do usuario
        Long userId = tokenService.getUserId(token);
        var userOpt = userDetailsRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new NotFoundExceptionAds("Usuário não encontrado");
        }

        UserCredentials userCredentials = userOpt.get();
        //autenticar no spring

        UsernamePasswordAuthenticationToken userAuth
                = new UsernamePasswordAuthenticationToken(userCredentials, null, userCredentials.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

    private String getBearerToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7);
    }

}
