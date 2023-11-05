package com.fastcampuspay.moneyservice.adapter.axon.event;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {

    @NotNull
    private String membershipId;

    public MemberMoneyCreatedEvent(@NotNull String targetMembershipId) {
        this.membershipId = targetMembershipId;
        this.validateSelf();
    }

    public MemberMoneyCreatedEvent() {
    }
}
