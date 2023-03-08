package com.fox.factory.controllres;

import com.fox.factory.entities.dto.UserDetailDto;
import com.fox.factory.entities.dto.security.AuthenticationRequestDto;
import com.fox.factory.entities.dto.security.Token;
import com.fox.factory.service.AuthenticationService;
import com.fox.factory.service.UserService;
import com.fox.factory.validation.OnCreate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fox.factory.controllres.responcecodes.HttpStatusCode.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserInfAuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    @Operation(summary = "Регистрация пользователя",
            description = "Регистрирует пользователя в системе и возвращает токен для аутентификации",
            responses = {
                    @ApiResponse(responseCode = OK, description = "Всё ок -- пользователь зарегистрирован. Возвращает токен"),
                    @ApiResponse(responseCode = UNAUTHORIZED, description = "Введены некорректные данные")
            }
    )
    @PostMapping("/register")
    public Token register(@RequestBody @Valid UserDetailDto user) {
        return authenticationService.register(user);
    }

    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентифицирует пользователя в системе и возвращает токен для аутентификации",
            responses = {
                    @ApiResponse(responseCode = OK, description = "Всё ок -- пользователь аутентифицирован. Возвращает токен"),
                    @ApiResponse(responseCode = UNAUTHORIZED, description = "Введены некорректные данные")
            }
    )
    @PostMapping("/authenticate")
    public Token authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Operation(
           summary = "User details",
            description = "Returns full info about user",
            responses = {
                   @ApiResponse(responseCode = OK, description = "User found and has acces to his page"),
                   @ApiResponse(responseCode = UNAUTHORIZED, description = "You must login to see cabinet"),
                   @ApiResponse(responseCode = FORBIDDEN, description = "No access to this user")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getfullInfo(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(
            summary = "User details update",
            description = "Updates user info and return  full info about user",
            responses = {
                    @ApiResponse(responseCode = OK, description = "User found and has acces to his page"),
                    @ApiResponse(responseCode = UNAUTHORIZED, description = "You must login "),
                    @ApiResponse(responseCode = FORBIDDEN, description = "No access to this user")
            }
    )
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDetailDto> updateInfo (@PathVariable Long id, @RequestBody UserDetailDto dto){
        return ResponseEntity.ok(userService.updateUserInfo(id, dto));
    }


    @Operation(
            summary = "Delete User",
            description = "Deetes user"
    )
    @DeleteMapping("/{id}/update")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
