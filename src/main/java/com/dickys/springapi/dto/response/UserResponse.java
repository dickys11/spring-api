package com.dickys.springapi.dto.response;

import com.dickys.springapi.config.annotation.UnixTime;
import com.dickys.springapi.config.serializer.LocalDateTimeUnixTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;

    @JsonSerialize(using = LocalDateTimeUnixTimeSerializer.class)
    @UnixTime
    private LocalDateTime createdAt;
}
