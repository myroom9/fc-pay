package com.fastcampuspay.moneyservice.adapter.axon.event;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

    private String aggregateIdentifier;
    private String targetMembershipId;
    private  int amount;

    public IncreaseMemberMoneyEvent(String aggregateIdentifier) {
        this.aggregateIdentifier = aggregateIdentifier;
    }

    public IncreaseMemberMoneyEvent(String aggregateIdentifier, String targetMembershipId, int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
    }
}
