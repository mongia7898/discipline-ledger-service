package com.mongia.discpline.ledger.services;

import com.mongia.discpline.ledger.DTOs.requests.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.requests.ExpenseSearchRequest;
import com.mongia.discpline.ledger.DTOs.requests.PageableRequest;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseListResponse;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseResponse;
import com.mongia.discpline.ledger.Entity.Expenses;
import com.mongia.discpline.ledger.Repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseService {


    private final  ExpenseRepository expenseRepository;

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
    public ExpenseListResponse getAllExpenses(PageableRequest pageableRequest){

        Sort sort = pageableRequest.getAscending() ? Sort.by(pageableRequest.getSortBy()).ascending() : Sort.by(pageableRequest.getSortBy()).descending();
        PageRequest pageRequest= PageRequest.of(pageableRequest.getPageNo(),pageableRequest.getSize(),sort);
         Page<Expenses> allExpenses=expenseRepository.findAll(pageRequest);
//         Long totalAmount=

         return convertPageableToList(allExpenses);
    }

    private ExpenseListResponse convertPageableToList(Page<Expenses> page){
        List<ExpenseResponse> list=new ArrayList<>();

        page.getContent().forEach(expense-> list.add(convertEntityToResponse(expense)));

        ExpenseListResponse expenseListResponse=new ExpenseListResponse();
        expenseListResponse.setExpenseResponses(list);
//        expenseListResponse.setTotalAmount();
        expenseListResponse.setCurrentPage(page.getNumber());
        expenseListResponse.setTotalPages(page.getTotalPages());
        expenseListResponse.setTotalElements(page.getTotalElements());
        return expenseListResponse;
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

    public ExpenseListResponse searchExpense(ExpenseSearchRequest expenseSearchRequest){
         return null;
    }
}
