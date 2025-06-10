package com.cryptotrading.cryptotrading.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends ResponseDto {

    private UUID id;

    private String username;

    private String password;

    private BigDecimal balance;

    @Override
    public boolean equals(Object other){

        if(other instanceof UserDto){
            return id == ((UserDto)other).getId();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
