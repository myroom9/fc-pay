package com.fastcampuspay.moneyservice.adapter.out.persistence;

import com.fastcampuspay.moneyservice.application.port.in.CreateMemberPort;
import com.fastcampuspay.moneyservice.application.port.in.GetMemberMoneyPort;
import com.fastcampuspay.moneyservice.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.moneyservice.domain.MemberMoney;
import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import com.fastcampuspay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, CreateMemberPort, GetMemberMoneyPort {

    private final SpringDataMoneyChangingRequestRepository bankAccountRepository;
    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus, MoneyChangingRequest.Uuid uuid) {
        MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity = new MoneyChangingRequestJpaEntity(
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getMoneyChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                new Timestamp(System.currentTimeMillis()),
                moneyChangingStatus.getChangingMoneyStatus(),
                uuid.getUuid()
        );
        return bankAccountRepository.save(moneyChangingRequestJpaEntity);
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;

        try {
            List<MemberMoneyJpaEntity> list = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = list.get(0);
            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    increaseMoneyAmount, ""
            );

            return memberMoneyRepository.save(entity);
        }
    }

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMembershipId()),
                0, aggregateIdentifier.getAggregateIdentifier()
        );

        memberMoneyRepository.save(entity);
    }

    @Override
    public MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId memberId) {
        MemberMoneyJpaEntity entity;
        List<MemberMoneyJpaEntity> entityList =  memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
        if(entityList.size() == 0){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    0, ""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
        return  entityList.get(0);
    }
}
