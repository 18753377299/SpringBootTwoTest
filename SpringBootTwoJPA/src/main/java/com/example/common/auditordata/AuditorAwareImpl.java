package com.example.common.auditordata;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("auditorAware")
public class AuditorAwareImpl  implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("{}==>"+authentication.getPrincipal().toString());
        return Optional.ofNullable(authentication.getPrincipal().toString());
    }

}
