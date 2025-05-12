package com.mongia.discpline.ledger.services;

import com.mongia.discpline.ledger.DTOs.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.ExpenseResponse;
import com.mongia.discpline.ledger.Entity.Expenses;
import com.mongia.discpline.ledger.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    public  ExpenseRepository expenseRepository;

     public ExpenseResponse createExpense(ExpenseRequest expenseRequest){
        Expenses expenses=Expenses.builder().amount(expenseRequest.getAmount())
                .category(expenseRequest.getCategory())
                .description(expenseRequest.getDescription())
                .dateTime(LocalDateTime.now()).build();

        Expenses savedExpense = expenseRepository.save(expenses);


        return convertEntityToResponse(savedExpense);

    }

    private static ExpenseResponse convertEntityToResponse(Expenses savedExpense) {
        return ExpenseResponse.builder()
                .id(savedExpense.getId())
                .amount(savedExpense.getAmount())
                .category(savedExpense.getCategory())
                .description(savedExpense.getDescription())
                .dateTime(savedExpense.getDateTime())
                .build();
    }

    public ExpenseResponse getExpenseById(Integer id){
        Optional<Expenses> expense = expenseRepository.findById(id);
        return expense.map(ExpenseService::convertEntityToResponse).orElse(null);
    }
    public List<ExpenseResponse> getAllExpenses(){
         List<Expenses> allExpenses=expenseRepository.findAll();
         List<ExpenseResponse> list=new ArrayList<>();
         allExpenses.forEach(expense-> list.add(convertEntityToResponse(expense)));
         return list;
    }


    public void deleteExpense(Integer id){
         expenseRepository.deleteById(id);
    }

    public ExpenseResponse editExpense(Integer id, ExpenseRequest request){
        Optional<Expenses> expense = expenseRepository.findById(id);
        if(expense.isPresent()){
            Expenses expenses = expense.get();
            expenses.setAmount(request.getAmount());
            expenses.setDescription(request.getDescription());
            expenses.setCategory(request.getCategory());
            return convertEntityToResponse(expenseRepository.save(expenses));
        }
        else return null;
    }
}
