package org.finance.casclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "cas")
@Getter
@Setter
public class CasProperties {
    private Server server = new Server();
    private Client client = new Client();
    private Security security = new Security();

    @Getter
    @Setter
    public static class Server {
        private String url;
        private String login;
        private String logout;
    }

    @Getter
    @Setter
    public static class Client {
        private String serviceUrl;
        private String callback;
    }

    @Getter
    @Setter
    public static class Security {
        private boolean enabled = true;
        private List<String> ignorePatterns = new ArrayList<>();
        private String roleAttributes = "roles";
    }
}