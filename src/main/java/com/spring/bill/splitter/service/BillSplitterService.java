package com.spring.bill.splitter.service;


import com.spring.bill.splitter.dto.request.BillRequest;
import com.spring.bill.splitter.dto.response.BillSplitResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface BillSplitterService  {
    BillSplitResult splitBill(BillRequest request);

}
