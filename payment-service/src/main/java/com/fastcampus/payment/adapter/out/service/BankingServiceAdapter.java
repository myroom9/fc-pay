package com.fastcampus.payment.adapter.out.service;

import com.fastcampus.payment.application.port.out.GetRegisteredBankAccountPort;
import com.fastcampus.payment.application.port.out.RegisteredBankAccountAggregateIdentifier;
import com.fastcampuspay.common.CommonHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;


    public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.banking.url}") String bankingServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = bankingServiceUrl;
    }


    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            ObjectMapper mapper = new ObjectMapper();

            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);

            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId(),
                    registeredBankAccount.getAggregateIdentifier(),
                    registeredBankAccount.getMembershipId(),
                    registeredBankAccount.getBankName(),
                    registeredBankAccount.getBankAccountNumber()
            );
        } catch (Exception e) {
            System.out.println("Banking SErvice adapter e = " + e);
            throw new RuntimeException(e);
        }
    }
}
