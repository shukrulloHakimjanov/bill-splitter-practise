package com.spring.bill.splitter.dto.request;

import com.spring.bill.splitter.dto.response.Item;
import com.spring.bill.splitter.dto.response.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    private List<Person> people;
    private List<Item> sharedItems;
    private BigDecimal serviceChargePercent;
}
