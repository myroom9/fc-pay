package com.fastcampuspay.moneyservice.application.service;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fastcampuspay.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.moneyservice.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.moneyservice.application.port.out.GetMembershipPort;
import com.fastcampuspay.moneyservice.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.moneyservice.application.port.out.SendRechargingMoneyTaskPort;
import com.fastcampuspay.moneyservice.domain.MemberMoney;
import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final GetMembershipPort membershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // ㅁㅓ니의 충전, 증액이라는 과정
        // 1. 고객 정보가 정상인지 확인
        membershipPort.getMembership(command.getTargetMembershipId());

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

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // 1. subtask, task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // banking account validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Amount money Firmbanking --> 무조건 ok 받았다고 가정
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 TASK")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("fastcampus bank")
                .build();

        // 2. kafka cluster produce
        // task를 produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // 3. wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task결과를 produce


        // 4. task result consume
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if (result.equals("success")) {
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
        } else {
            return null;
        }

        // 5. consume ok, Logic

        return null;
    }
}
