package com.mongia.discpline.ledger.services;

import com.mongia.discpline.ledger.DTOs.requests.HabitRequest;
import com.mongia.discpline.ledger.Entity.Habit;
import com.mongia.discpline.ledger.Repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    public Habit createHabit(HabitRequest habitRequest){
        Habit habit=new Habit();
        habit.setHabitName(habitRequest.getName());
        habit.setDescription(habitRequest.getDescription());

        return habitRepository.save(habit);
    }

    public List<Habit> getHabits(){
        return habitRepository.findAll();
    }

    public Habit updateHabit(int id, HabitRequest request){
        Optional<Habit> findById=habitRepository.findById(id);
        if(findById.isEmpty()){
            throw new ConfigDataResourceNotFoundException(null);
        }
        Habit habit = findById.get();
        habit.setDescription(request.getDescription());
        habit.setHabitName(request.getName());
        return habitRepository.save(habit);
    }
    public void deleteHabit(int id){
        habitRepository.deleteById(id);
    }

    public Habit getHabitById(Integer id) {
        Optional<Habit> findById=habitRepository.findById(id);
        if(findById.isEmpty()){
            return null;
//            throw new NoResourceFoundException(HttpMethod.POST,"Resource not found with id: " + id);
        }
        return findById.get();
    }
}
