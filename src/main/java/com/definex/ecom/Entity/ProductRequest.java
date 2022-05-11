package com.definex.ecom.Entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Setter
@Getter
@Entity(name = "Product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150607L;

    @Id
    @GeneratedValue
    private int productId;
    private double price;
    private String productName;
    private int amount;
    private boolean buy1get1;
    private String type;
    private double discountValue;
    private boolean discountWithCode;


}
