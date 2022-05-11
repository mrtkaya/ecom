package com.definex.ecom.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Setter
@Getter
@Entity( name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest implements Serializable {

    private static final long serialVersionUID = 5546468583005150607L;

    @Id
    @GeneratedValue
    private int customerId;
    private String name;
    private String surname;
    private String email;
    private String phone;
}
