package com.definex.ecom.Model;


import com.definex.ecom.Entity.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket implements Serializable {

    private static final long serialVersionUID = 5926467183005150607L;

    private int customerId;
    private ProductRequest productRequest;
}
