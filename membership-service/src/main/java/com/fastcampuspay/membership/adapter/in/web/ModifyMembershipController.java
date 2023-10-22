package com.fastcampuspay.membership.adapter.in.web;

import com.fastcampuspay.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipUseCase;
import com.fastcampuspay.membership.domain.Membership;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @GetMapping("/membership/modify/{membershipId}")
    public ResponseEntity<Membership> modifyMembershipByMembershipId(ModifyMembershipRequest request) {
        // request~~
        // request -> command
        // usercase ~~ (command)

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .name(request.getName())
                .membershipId(request.getMembershipId())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();


        return ResponseEntity.ok().body(modifyMembershipUseCase.modifyMembership(command));
    }

}
