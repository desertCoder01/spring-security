package com.example.demo.model.constant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AppConstants {

    public static final String ERROR_MESSAGE = "Error while processing the request ";
    public static final long CURRENT_TIME = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
}
