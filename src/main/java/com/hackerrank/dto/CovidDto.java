package com.hackerrank.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CovidDto {

    private String country;
    private Integer cases;
    private Integer recovered;
    private Integer deaths;
}

