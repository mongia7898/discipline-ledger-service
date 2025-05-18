package com.mongia.discpline.ledger.Repository;

import com.mongia.discpline.ledger.Entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit,Integer> {
}
