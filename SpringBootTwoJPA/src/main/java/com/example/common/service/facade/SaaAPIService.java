package com.example.common.service.facade;

import org.springframework.data.jpa.domain.Specification;

public interface SaaAPIService {
    public Specification addPower(String taskCode);
}
