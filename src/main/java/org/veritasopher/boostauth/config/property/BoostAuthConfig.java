package org.veritasopher.boostauth.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Profile Property
 *
 * @author Yepeng Ding
 */
@Component
@Getter
public class BoostAuthConfig {

    @Value("${boostauth.profile}")
    private String profile;

    @Value("${boostauth.admin.username}")
    private String adminUsername;

    @Value("${boostauth.admin.password}")
    private String adminPassword;

    public boolean isLocal() {
        return "local".equals(profile);
    }

    public boolean isDevelopment() {
        return "development".equals(profile);
    }

    public boolean isProduction() {
        return "production".equals(profile);
    }

}
