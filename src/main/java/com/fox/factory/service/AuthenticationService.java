package com.fox.factory.service;

import com.fox.factory.entities.dto.UserDetailDto;
import com.fox.factory.entities.dto.security.AuthenticationRequestDto;
import com.fox.factory.entities.dto.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public Token register(UserDetailDto userRegistryDTO) {
        var user = userService.save(userRegistryDTO);
        var jwtToken = jwtService.generateToken(user);
        return new Token(jwtToken);
    }

    public Token authenticate(AuthenticationRequestDto request) {
        auth(request);
        var user = userService.getByUsername(request.getUsername());
        return new Token(jwtService.generateToken(user));
    }

    private void auth(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword(),
                        new ArrayList<>()
                )
        );
    }
}