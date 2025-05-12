package com.mongia.discpline.ledger.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="expenses")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expenses {
    @Id
    private int id;

    private Double amount;
    private String category;
    private String description;
    private LocalDateTime dateTime;
}
