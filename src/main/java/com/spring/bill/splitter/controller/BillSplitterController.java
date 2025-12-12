package com.spring.bill.splitter.controller;

import com.spring.bill.splitter.dto.request.BillRequest;
import com.spring.bill.splitter.dto.response.BillSplitResult;
import com.spring.bill.splitter.service.BillSplitterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/bill")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillSplitterController {

    BillSplitterService billSplitterService;

    @PostMapping("/split")
    public ResponseEntity<BillSplitResult> splitBill(@Valid @RequestBody BillRequest request) {
        BillSplitResult result = billSplitterService.splitBill(request);
        return ResponseEntity.ok(result);

    }

}