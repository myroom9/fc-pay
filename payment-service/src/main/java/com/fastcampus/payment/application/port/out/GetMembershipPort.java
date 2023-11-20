package com.fastcampus.payment.application.port.out;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);

}
