package com.gsdc.server.dto.error;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String message;
    private final Boolean show;
    private String stackTrace;

    public ErrorResponse(int status, String message, Boolean show, String stackTrace) {
        this.status = status;
        this.message = message;
        this.show = show;
        this.stackTrace = stackTrace;
    }

}
