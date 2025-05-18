package com.mongia.discpline.ledger.DTOs.responses;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseListResponse {
    private Double totalAmount;
    private int totalPages;
    private int currentPage;
    private Long totalElements;
    private List<ExpenseResponse> expenseResponses;
}
