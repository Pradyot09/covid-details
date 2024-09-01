package com.hackerrank.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Covid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String country;
    private Integer cases;
    private Integer recovered;
    private Integer deaths;
}
