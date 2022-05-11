package com.definex.ecom.Model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    private double discountPrice;
    private int productId;
    private String type;
    private int customerId;

}
