package org.acme.dto.response.auth;

import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
