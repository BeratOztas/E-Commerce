package com.beratoztas.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private Long addressId;

    private String city;
    private String district;
    private String neighborhood;
    private String street;
}
