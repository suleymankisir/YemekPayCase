package com.yemeksepeti.YemekPayCase.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
    private  int id;

    private  String title;

    private  String description;

    private LocalDate dueDate;

    private boolean completed;
}
