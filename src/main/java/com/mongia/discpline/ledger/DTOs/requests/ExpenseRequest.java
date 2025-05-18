package com.mongia.discpline.ledger.DTOs.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExpenseRequest {

    @NotBlank(message = "category cannot be empty")
    @Size(min=1, max=255,message = "Category size should be between 1 to 255")
    private String category;

    @NotNull
    @Min(value=0, message ="should be greater than zero" )
    @Max(value = 100000,message = "Should be less than a lakh")
    private Double amount;

    @NotBlank(message = "Description cannot be empty")
    @Size(min=1, max=255,message = "Description size should be between 1 to 255")
    private String description;
}
