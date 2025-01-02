package org.acme.exception;

import jakarta.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomException extends RuntimeException {
    private final Response.Status status;

    public CustomException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
