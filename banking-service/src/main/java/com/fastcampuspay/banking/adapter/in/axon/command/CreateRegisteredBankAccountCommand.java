package com.fastcampuspay.banking.adapter.in.axon.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAccountCommand {

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

}
