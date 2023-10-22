package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.domain.Membership;
import com.fastcampuspay.common.UseCase;

@UseCase
public interface ModifyMembershipUseCase {

    Membership modifyMembership(ModifyMembershipCommand command);

}
