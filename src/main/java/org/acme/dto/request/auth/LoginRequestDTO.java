package org.acme.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * Github: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRequestDTO {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
