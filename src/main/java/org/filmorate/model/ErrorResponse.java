package org.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
