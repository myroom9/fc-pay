package com.fastcampuspay.moneyservice.adapter.out.service;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.moneyservice.application.port.out.GetMembershipPort;
import com.fastcampuspay.moneyservice.application.port.out.MembershipStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;
    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {

        String url = String.join("/", membershipServiceUrl, "membership", membershipId);

        try {
            // 실제로 http call로 membership service를 호출
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            }

            return new MembershipStatus(membership.getMembershipId(), false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
