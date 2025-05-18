package com.mongia.discpline.ledger.DTOs.requests;


import lombok.Data;
import java.util.List;

@Data
public class ExpenseSearchRequest extends PageableRequest {
    private String startDate;
    private String endDate;
    private List<String> category;
}
