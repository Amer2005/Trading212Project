package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.request.AuthRequestDto;
import com.cryptotrading.cryptotrading.domain.dto.request.RequestDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private ResponseCookie SetSeesionCookie(UUID session)
    {
        ResponseCookie cookie = ResponseCookie.from("SESSIONID", session.toString())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return cookie;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody AuthRequestDto userInfo) {

        UserResponseDto userResponse = new UserResponseDto();

        userResponse = authenticationService.registerUser(userInfo.getUsername(), userInfo.getPassword());

        if(!userResponse.getStatus())
        {
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
        }

        ResponseCookie cookie = SetSeesionCookie(userResponse.getSession());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody AuthRequestDto userInfo) {

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto = authenticationService.loginUser(userInfo.getUsername(), userInfo.getPassword());

        if(!userResponseDto.getStatus()) {
            return new ResponseEntity<>(userResponseDto, HttpStatus.UNAUTHORIZED);
        }

        ResponseCookie cookie = SetSeesionCookie(userResponseDto.getSession());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(userResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logoutUser(@RequestBody RequestDto requestDto) {
        ResponseDto response = new UserResponseDto();

        response = authenticationService.logoutUser(requestDto.getSession());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
