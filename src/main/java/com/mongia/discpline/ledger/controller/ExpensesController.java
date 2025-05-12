package com.mongia.discpline.ledger.controller;

import com.mongia.discpline.ledger.DTOs.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.ExpenseResponse;
import com.mongia.discpline.ledger.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<ExpenseResponse> getAllExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    ExpenseResponse getExpenseById(@PathVariable Integer id){
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Integer id){
        expenseService.deleteExpense(id);
        return "Expense deleted successfully";
    }

    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Integer id,@RequestBody ExpenseRequest expenseRequest){
        return expenseService.editExpense(id,expenseRequest);
    }
}
