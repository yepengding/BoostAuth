package org.veritasopher.boostauth.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Crypto Utils
 *
 * @author Yepeng Ding
 */
public class CryptoUtils {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Encode by BCryptPassword
     *
     * @param plaintext plaintext
     * @return ciphertext
     */
    public static String encodeByBCrypt(String plaintext) {
        return bCryptPasswordEncoder.encode(plaintext);
    }

    /**
     * Match plaintext with ciphertext
     *
     * @param plaintext  plaintext
     * @param ciphertext ciphertext
     * @return true if plaintext matches ciphertext
     */
    public static boolean matchByBCrypt(String plaintext, String ciphertext) {
        return bCryptPasswordEncoder.matches(plaintext, ciphertext);
    }
}
