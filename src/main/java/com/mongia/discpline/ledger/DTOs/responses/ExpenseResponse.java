package com.mongia.discpline.ledger.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private Integer id;
    private String category;
    private Double amount;
    private String description;
    private LocalDateTime dateTime;
}
