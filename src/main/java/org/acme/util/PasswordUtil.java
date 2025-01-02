package org.acme.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * Github: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordUtil {

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
