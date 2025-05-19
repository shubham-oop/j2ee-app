package com.example.student.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer roll_number;

    @Column(nullable = false)
    private String name;

    private Integer age;
    private Long phone_number;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String address;
}
