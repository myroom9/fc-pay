package com.fastcampuspay.moneyservice.application.port.out;

import com.fastcampuspay.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    void sendRechargingMoneyTaskPort(
            RechargingMoneyTask task
    );
}
