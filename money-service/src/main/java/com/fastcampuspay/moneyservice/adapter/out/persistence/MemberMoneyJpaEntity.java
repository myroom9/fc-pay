package com.fastcampuspay.moneyservice.adapter.out.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_money")
@Data
@NoArgsConstructor
public class MemberMoneyJpaEntity {

    @Id
    @GeneratedValue
    private Long memberMoneyId;

    private Long memberId;

    private int balance;

    public MemberMoneyJpaEntity(Long memberMoneyId, Long memberId, int balance) {
        this.memberMoneyId = memberMoneyId;
        this.memberId = memberId;
        this.balance = balance;
    }

    public MemberMoneyJpaEntity(Long memberId, int balance) {
        this.memberId = memberId;
        this.balance = balance;
    }
}
