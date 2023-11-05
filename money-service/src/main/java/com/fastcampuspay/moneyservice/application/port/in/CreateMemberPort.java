package com.fastcampuspay.moneyservice.application.port.in;

import com.fastcampuspay.moneyservice.domain.MemberMoney;

public interface CreateMemberPort {

    void createMemberMoney(
            MemberMoney.MembershipId memberId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );

}
