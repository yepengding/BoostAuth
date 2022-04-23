package org.veritasopher.boostauth.config;

public interface GlobalKey {

    String ISSUER = "BoostAuth";

    // TODO Do not use this key in production
    // NIST SP 800-117
    String JWT_SIGNING_KEY = "BoostAuth-@JWT!Secret#key.33c2cfb73acefce30da6a3945cac1b81dda8fee352b3a065cc565b5a96806abd";
}
