package com.spring.bill.splitter.service.impl;


import com.spring.bill.splitter.dto.request.BillRequest;
import com.spring.bill.splitter.dto.response.BillSplitResult;
import com.spring.bill.splitter.dto.response.Item;
import com.spring.bill.splitter.dto.response.Person;
import com.spring.bill.splitter.dto.response.PersonExpense;
import com.spring.bill.splitter.service.BillSplitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillSplitterServiceImpl implements BillSplitterService {

    private static final int DECIMAL_SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    @Override
    public BillSplitResult splitBill(BillRequest request) {
        validateRequest(request);

        int peopleCount = request.getPeople().size();
        BigDecimal sharedTotal = calculateSharedItemsTotal(request.getSharedItems());
        BigDecimal sharedPerPerson = sharedTotal.divide(
                BigDecimal.valueOf(peopleCount), DECIMAL_SCALE, ROUNDING_MODE);

        List<PersonExpense> expenses = new ArrayList<>();
        BigDecimal totalBill = BigDecimal.ZERO;
        BigDecimal totalServiceChargeSum = BigDecimal.ZERO;

        for (Person person : request.getPeople()) {
            BigDecimal personalTotal = calculatePersonalItemsTotal(person);
            BigDecimal subtotal = personalTotal.add(sharedPerPerson);

            BigDecimal serviceCharge = subtotal
                    .multiply(request.getServiceChargePercent())
                    .divide(BigDecimal.valueOf(100), DECIMAL_SCALE, ROUNDING_MODE);

            BigDecimal total = subtotal.add(serviceCharge);

            expenses.add(new PersonExpense(
                    person.getName(),
                    personalTotal,
                    sharedPerPerson,
                    subtotal,
                    serviceCharge,
                    total
            ));

            totalBill = totalBill.add(subtotal);
            totalServiceChargeSum = totalServiceChargeSum.add(serviceCharge);
        }

        BigDecimal grandTotal = totalBill.add(totalServiceChargeSum);

        return new BillSplitResult(expenses, totalBill, totalServiceChargeSum, grandTotal);
    }

    private void validateRequest(BillRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getPeople() == null || request.getPeople().isEmpty()) {
            throw new IllegalArgumentException("At least one person is required");
        }
        if (request.getServiceChargePercent() == null ||
                request.getServiceChargePercent().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Service charge must be non-negative");
        }
    }

    private BigDecimal calculateSharedItemsTotal(List<Item> sharedItems) {
        if (sharedItems == null || sharedItems.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return sharedItems.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePersonalItemsTotal(Person person) {
        if (person.getItems() == null || person.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return person.getItems().stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

