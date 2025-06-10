package com.cryptotrading.cryptotrading.domain.dto.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDto extends RequestDto {

    private String username;

    private String password;

}
