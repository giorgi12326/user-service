package org.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponseDTO {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private List<String> details;
}