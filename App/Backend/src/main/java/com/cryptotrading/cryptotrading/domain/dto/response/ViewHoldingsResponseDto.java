package com.cryptotrading.cryptotrading.domain.dto.response;

import com.cryptotrading.cryptotrading.domain.Holding;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewHoldingsResponseDto extends ResponseDto {

    public ViewHoldingsResponseDto() {
        holdings = new ArrayList<>();
    }

    public ViewHoldingsResponseDto(List<HoldingDto> holdings) {
        this.holdings = holdings;
    }

    private List<HoldingDto> holdings;

}
