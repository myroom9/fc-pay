package com.fastcampuspay.moneyservice.application.port.out;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);

}
