package com.fastcampuspay.membership.application.port.in;


import common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {

    @NotNull
    private final String membershipId;


    public FindMembershipCommand(String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
