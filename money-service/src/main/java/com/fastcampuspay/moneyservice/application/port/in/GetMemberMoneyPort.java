package com.fastcampuspay.moneyservice.application.port.in;

import com.fastcampuspay.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.moneyservice.domain.MemberMoney;

public interface GetMemberMoneyPort {

    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );

}
