package com.quiz.application.QuizApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PartialUserDTO {
    private int id;
    private String username;
    private String password;
    private String role;
}
