package dev.events.authentication.config;

import dev.events.authentication.session.SessionContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor sessionRequestInterceptor() {
        return new SessionRequestInterceptor();
    }
}

class SessionRequestInterceptor implements RequestInterceptor {

    private static final String ID_HEADER = "id";
    private static final String EMAIL_HEADER = "email";
    private static final String ROLES_HEADER = "roles";
    private static final String CORRELATION_ID_HEADER = "correlationId";

    @Override
    public void apply(RequestTemplate template) {

        template.header(ID_HEADER, SessionContextHolder.getSession().getId());
        template.header(EMAIL_HEADER, SessionContextHolder.getSession().email());
        template.header(ROLES_HEADER, SessionContextHolder.getSession().getRoles());
        template.header(CORRELATION_ID_HEADER, SessionContextHolder.getSession().correlationId().toString());
    }
}