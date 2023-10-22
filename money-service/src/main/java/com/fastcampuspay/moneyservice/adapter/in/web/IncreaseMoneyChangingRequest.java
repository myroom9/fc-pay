package com.fastcampuspay.moneyservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseMoneyChangingRequest {
    private String targetMembershipId;
    private int amount;
}
