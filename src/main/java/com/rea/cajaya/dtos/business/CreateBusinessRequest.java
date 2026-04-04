package com.rea.cajaya.dtos.business;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class CreateBusinessRequest {
    private String name;
    private String plan;
}
