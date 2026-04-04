package com.rea.cajaya.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "businesses")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String plan;
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "business")
    private List<User> users;

    @OneToMany(mappedBy = "business")
    private List<Product> products;

    @OneToMany(mappedBy = "business")
    private List<Sale> sales;

    @OneToMany(mappedBy = "business")
    private List<Expense> expenses;
}
