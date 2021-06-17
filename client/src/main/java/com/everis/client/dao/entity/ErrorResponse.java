package com.everis.client.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse extends DataResponse{
    private HttpStatus status;
    private String message;
}
