package com.mongia.discpline.ledger.controller;

import com.mongia.discpline.ledger.CommonUtils.Response.LedgerResponse;
import com.mongia.discpline.ledger.DTOs.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.ExpenseResponse;
import com.mongia.discpline.ledger.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    @Autowired
    public  ExpenseService expenseService;

    @PostMapping
     ExpenseResponse addExpense(@RequestBody ExpenseRequest expenseRequest){
        return expenseService.createExpense(expenseRequest);
    }

    @GetMapping
    public ResponseEntity<LedgerResponse<List<ExpenseResponse>>> getAllExpenses(){
        List<ExpenseResponse> allExpenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(new LedgerResponse<List<ExpenseResponse>>(Boolean.TRUE,"Fetched all expenses",allExpenses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Integer id){
        ExpenseResponse expenseById = expenseService.getExpenseById(id);
        return ResponseEntity.ok(new LedgerResponse<ExpenseResponse>(Boolean.TRUE,"Get by id",expenseById));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Integer id){
        expenseService.deleteExpense(id);
        return ResponseEntity.ok( new LedgerResponse<String>(Boolean.TRUE,"Deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Integer id,@RequestBody ExpenseRequest expenseRequest){
        return ResponseEntity.ok(new LedgerResponse<ExpenseResponse>(Boolean.TRUE,"Get by id",expenseService.editExpense(id,expenseRequest)));
    }
}
