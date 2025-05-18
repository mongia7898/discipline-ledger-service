package com.mongia.discpline.ledger.controller;

import com.mongia.discpline.ledger.CommonUtils.Response.LedgerResponse;
import com.mongia.discpline.ledger.CommonUtils.ResponseMessages;
import com.mongia.discpline.ledger.DTOs.requests.HabitRequest;
import com.mongia.discpline.ledger.services.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController extends ResponseMessages {

    private final HabitService habitService;
    @PostMapping
    public ResponseEntity<LedgerResponse<?>> createHabit(@RequestBody HabitRequest habitRequest){
        return ResponseEntity.ok(new LedgerResponse<>(Boolean.TRUE,HABIT_CREATED,habitService.createHabit(habitRequest)));
    }

    @GetMapping
    public ResponseEntity<LedgerResponse<?>> getHabits(){
        return ResponseEntity.ok(new LedgerResponse<>(Boolean.TRUE,HABIT_FETCHED,habitService.getHabits()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LedgerResponse<?>> getHabitById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(new LedgerResponse<>(Boolean.TRUE,HABIT_FETCHED,habitService.getHabitById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LedgerResponse<?>> deleteHabit(@PathVariable("id") Integer id){
        habitService.deleteHabit(id);
        return ResponseEntity.ok(new LedgerResponse<>(Boolean.TRUE,HABIT_DELETED));
    }

    @PostMapping("/{id}")
    public ResponseEntity<LedgerResponse<?>> updateHabit(@PathVariable Integer id,@RequestBody HabitRequest habitRequest){
        return ResponseEntity.ok(new LedgerResponse<>(Boolean.TRUE,HABIT_CREATED,habitService.updateHabit(id,habitRequest)));
    }



}
