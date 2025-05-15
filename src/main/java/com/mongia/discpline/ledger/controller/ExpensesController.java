package com.mongia.discpline.ledger.controller;

import com.mongia.discpline.ledger.CommonUtils.Response.LedgerResponse;
import com.mongia.discpline.ledger.CommonUtils.ResponseMessages;
import com.mongia.discpline.ledger.DTOs.requests.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.requests.ExpenseSearchRequest;
import com.mongia.discpline.ledger.DTOs.requests.PageableRequest;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseListResponse;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseResponse;
import com.mongia.discpline.ledger.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpensesController extends ResponseMessages {


    private final  ExpenseService expenseService;

    @PostMapping
     ExpenseResponse addExpense(@Valid @RequestBody  ExpenseRequest expenseRequest){
        return expenseService.createExpense(expenseRequest);
    }

    @PostMapping("/all")
    public ResponseEntity<LedgerResponse<?>> getAllExpenses(@RequestBody PageableRequest pageableRequest){
        ExpenseListResponse allExpenses = expenseService.getAllExpenses(pageableRequest);
        return ResponseEntity.ok(new LedgerResponse<ExpenseListResponse>(Boolean.TRUE,EXPENSE_CREATED,allExpenses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Integer id){
        ExpenseResponse expenseById = expenseService.getExpenseById(id);
        return ResponseEntity.ok(new LedgerResponse<ExpenseResponse>(Boolean.TRUE,EXPENSE_FETCHED,expenseById));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Integer id){
        expenseService.deleteExpense(id);
        return ResponseEntity.ok( new LedgerResponse<String>(Boolean.TRUE,EXPENSE_DELETED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Integer id,@Valid @RequestBody  ExpenseRequest expenseRequest){
        return ResponseEntity.ok(new LedgerResponse<ExpenseResponse>(Boolean.TRUE,EXPENSE_UDPATED,expenseService.editExpense(id,expenseRequest)));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchExpenses(@RequestBody ExpenseSearchRequest expenseSearch){
        return ResponseEntity.ok(new LedgerResponse<ExpenseListResponse>(Boolean.TRUE,EXPENSE_UDPATED,expenseService.searchExpense(expenseSearch)));

    }
}
