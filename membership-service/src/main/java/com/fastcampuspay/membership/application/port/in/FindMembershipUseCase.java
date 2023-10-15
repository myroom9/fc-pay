package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.domain.Membership;
import common.UseCase;

@UseCase
public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);

}
