package com.example.common.auditordata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

/*审计*/
@Slf4j
@Component("auditorAware")
public class AuditorAwareImpl  implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            log.info("{}==>"+authentication.getPrincipal().toString());
//            return Optional.ofNullable(authentication.getPrincipal().toString());
            System.out.println("{}========================>");
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
