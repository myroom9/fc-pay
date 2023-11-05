package com.fastcampuspay.banking.adapter.in.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FirmbankingRequestUpdatedEvent {

    private int firmbankingStatus;

}
