package com.james.jasperreportstest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    private Long id;
    private String name;
    private  String department;
    private  double salary;
    private Date hd;
}
