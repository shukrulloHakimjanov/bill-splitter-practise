package com.spring.bill.splitter.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillSplitResult {
    private List<PersonExpense> expenses;
    private BigDecimal totalBill;
    private BigDecimal totalServiceCharge;
    private BigDecimal grandTotal;
}
