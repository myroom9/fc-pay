package com.fastcampuspay.moneyservice.application.service;

import com.fastcampuspay.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.moneyservice.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.moneyservice.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.moneyservice.domain.MemberMoney;
import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // ㅁㅓ니의 충전, 증액이라는 과정
        // 1. 고객 정보가 정상인지 확인
        // 2. 고객의 연동된 계좌가 있는지, 그리고 정상적인지 확인
        // 3. 법인 계좌 상태도 정상인지 확인
        // 4. 증액을 위한 "기록"
        // 5. 펌뱅킹을 수행함
        // 6. 결과 성공
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

        if (!ObjectUtils.isEmpty(memberMoneyJpaEntity)) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(1),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            ));
        }

        // 6-1. 결과 실패
        return null;
    }
}
