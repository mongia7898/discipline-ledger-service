package com.mongia.discpline.ledger.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExpenseRequest {

    private String category;
    private Double amount;
    private String description;
}
