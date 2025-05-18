package com.mongia.discpline.ledger.services;

import com.mongia.discpline.ledger.DTOs.requests.ExpenseRequest;
import com.mongia.discpline.ledger.DTOs.requests.ExpenseSearchRequest;
import com.mongia.discpline.ledger.DTOs.requests.PageableRequest;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseListResponse;
import com.mongia.discpline.ledger.DTOs.responses.ExpenseResponse;
import com.mongia.discpline.ledger.Entity.Expenses;
import com.mongia.discpline.ledger.Repository.ExpenseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ExpenseService {


    private final  ExpenseRepository expenseRepository;

    private final EntityManager entityManager;
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

        Double totalAmount=getTotalAmount(List.of());
         return convertPageableToList(allExpenses,totalAmount);
    }

    private ExpenseListResponse convertPageableToList(Page<Expenses> page,Double totalAmount){
        List<ExpenseResponse> list=new ArrayList<>();

        page.getContent().forEach(expense-> list.add(convertEntityToResponse(expense)));

        ExpenseListResponse expenseListResponse=new ExpenseListResponse();
        expenseListResponse.setExpenseResponses(list);
        expenseListResponse.setTotalAmount(totalAmount);
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

    //TODO: Complete the function
    public ExpenseListResponse searchExpense(PageableRequest pageableRequest,ExpenseSearchRequest expenseSearchRequest){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expenses> query=cb.createQuery(Expenses.class);
        Root<Expenses> root = query.from(Expenses.class);
        List<Predicate> predicates=new ArrayList<>();

         if(expenseSearchRequest.getCategory()!=null && !expenseSearchRequest.getCategory().isEmpty()){
             predicates.add(root.get("category").in(expenseSearchRequest.getCategory()));
         }
         if(expenseSearchRequest.getStartDate()!=null){
             predicates.add(cb.greaterThanOrEqualTo(root.get("dateTime"),expenseSearchRequest.getStartDate()));
         }
        if(expenseSearchRequest.getEndDate()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("dateTime"),expenseSearchRequest.getEndDate()));
        }
        TypedQuery<Expenses> typedQuery = entityManager.createQuery(query);
        List<Expenses> expenses = typedQuery.getResultList();
        Double totalAmount = getTotalAmount(predicates);
        ExpenseListResponse expenseListResponse=new ExpenseListResponse();
        expenseListResponse.setTotalPages(1);
        expenseListResponse.setTotalAmount(totalAmount);
        expenseListResponse.setCurrentPage(1);
        List<ExpenseResponse> list=new ArrayList<>();

        expenses.forEach(expense-> list.add(convertEntityToResponse(expense)));
        expenseListResponse.setExpenseResponses(list);
        return  expenseListResponse;
     }

    private Double getTotalAmount(List<Predicate> predicates){
         CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query=cb.createQuery(Double.class);
        Root<Expenses> root = query.from(Expenses.class);

        query.select(cb.sum(root.get("amount")).as(Double.class));
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getSingleResult();

    }
}
