package com.fastcampuspay.banking.adapter.in.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FirmbankingRequestCreatedEvent {

    private String fromBankName;
    private String fromBankAccountName;

    private String toBankName;
    private String toBankAccountName;

    private int moneyAmount;

}
