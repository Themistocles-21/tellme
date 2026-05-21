package com.cscd488.html.services;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class ServiceToggleService {

    private final Set<String> disabledServices = new HashSet<>();

    public Set<String> getDisabledServices() {
        return disabledServices;
    }

    public void disableService(String serviceKey) {
        disabledServices.add(serviceKey);
    }

    public void enableService(String serviceKey) {
        disabledServices.remove(serviceKey);
    }

    public boolean isDisabled(String serviceKey) {
        return disabledServices.contains(serviceKey);
    }
}