package com.everis.client.dao.entity.personal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalError extends ClientPersonal {
    @JsonProperty("Status")
    private HttpStatus status;
    @JsonProperty("Message")
    private String message;
}
