package com.ads.clien.plus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionsTypeDTO {

    private Long id;
    private String name;
    private Long accessMonth;
    private BigDecimal price;
    private String productKey;
}
