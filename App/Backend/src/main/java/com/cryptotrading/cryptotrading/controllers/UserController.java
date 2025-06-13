package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.dto.request.RequestDto;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewHoldingsResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewTransactionsResponseDto;
import com.cryptotrading.cryptotrading.services.HoldingService;
import com.cryptotrading.cryptotrading.services.TransactionService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final HoldingService holdingService;
    private final TransactionService transactionService;

    public UserController(UserService userService, HoldingService holdingService, TransactionService transactionService) {
        this.userService = userService;
        this.holdingService = holdingService;
        this.transactionService = transactionService;
    }

    @PostMapping("/reset")
    public void reset(@RequestBody RequestDto requestDto) {
        userService.resetUser(requestDto.getSession());
    }

    @PostMapping("/user/get")
    public ResponseEntity<UserResponseDto> getUser(@RequestBody RequestDto requestDto) {
        UserResponseDto user = userService.getUserDtoBySession(requestDto.getSession());

        if(!user.getStatus())
        {
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/holdings")
    public ResponseEntity<ViewHoldingsResponseDto> getHoldings(@RequestBody RequestDto requestDto)
    {
        ViewHoldingsResponseDto responseDto = new ViewHoldingsResponseDto();

        UserResponseDto user = userService.getUserDtoBySession(requestDto.getSession());

        if(!user.getStatus())
        {
            responseDto.setStatus(false);
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

        ViewHoldingsResponseDto holdings = holdingService.getUserHoldings(user.getId());

        if(!holdings.getStatus())
        {
            return new ResponseEntity<>(holdings, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(holdings, HttpStatus.OK);
    }

    @PostMapping("/user/transactions")
    public ResponseEntity<ViewTransactionsResponseDto> getTransactions(@RequestBody RequestDto requestDto)
    {
        ViewTransactionsResponseDto responseDto = new ViewTransactionsResponseDto();

        UserResponseDto user = userService.getUserDtoBySession(requestDto.getSession());

        if(!user.getStatus())
        {
            responseDto.setStatus(false);
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

        ViewTransactionsResponseDto transactions = transactionService.getUserTransactions(user.getId());

        if(!transactions.getStatus())
        {
            return new ResponseEntity<>(transactions, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
