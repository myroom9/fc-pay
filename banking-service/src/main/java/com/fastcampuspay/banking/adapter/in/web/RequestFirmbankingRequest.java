package com.fastcampuspay.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFirmbankingRequest {
    private String fromBankAccountNumber;
    private String fromBankName;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
}
