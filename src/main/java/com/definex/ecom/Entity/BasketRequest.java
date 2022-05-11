package com.definex.ecom.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Setter
@Getter
@Entity(name = "Basket")
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequest implements Serializable {

    private static final long serialVersionUID = 5926468323005150607L;

    @Id
    @GeneratedValue
    private int basketId;
    private int productId;
    private int customerId;
    private int amount;
    private String discountType;
    private boolean discountWithCode;
    private double discountValue = 0;
    private double lastDiscountPrice;

    public boolean isDiscountWithCode() {
        return discountWithCode;
    }

    public void setDiscountWithCode(boolean discountWithCode) {
        this.discountWithCode = discountWithCode;
    }
}
