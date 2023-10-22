package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.service.Membership;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);

}
