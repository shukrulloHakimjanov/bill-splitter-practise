package com.spring.bill.splitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonExpense {
    private String personName;
    private BigDecimal personalItems;
    private BigDecimal sharedItems;
    private BigDecimal subtotal;
    private BigDecimal serviceCharge;
    private BigDecimal total;
}
