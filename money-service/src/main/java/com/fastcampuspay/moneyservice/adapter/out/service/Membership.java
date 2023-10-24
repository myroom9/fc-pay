package com.fastcampuspay.moneyservice.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * for banking service를 위함
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    private String membershipId;
    private String name;
    private String email;
    private String address;
    private boolean isValid;
    private boolean isCorp;

}
