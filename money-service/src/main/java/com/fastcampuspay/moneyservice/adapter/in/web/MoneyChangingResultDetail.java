package com.fastcampuspay.moneyservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;
    private int moneyChangingResultStatus;
    private int moneyChangingType;
    private int amount;
}