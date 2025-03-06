package io.notecalendar.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors")
public class AllowedOriginsConfig {
    private List<String> allowed;

    public List<String> getAllowed() {
        return allowed;
    }

    public void setAllowed(List<String> allowed) {
        this.allowed = allowed;
    }

    public AllowedOriginsConfig() {
    }

    public AllowedOriginsConfig(List<String> allowed) {
        this.allowed = allowed;
    }
}
