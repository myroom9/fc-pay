package com.fastcampuspay.banking.adapter.in.axon.command;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateFirmbankingRequestCommand {

    private String fromBankName;
    private String fromBankAccountNumber;

    private String toBankName;
    private String toBankAccountNumber;

    private int moneyAmount;

}
