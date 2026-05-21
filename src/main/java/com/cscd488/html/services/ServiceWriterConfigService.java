package com.cscd488.html.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceWriterConfigService {
    private String serviceWriterEmail = "deriklittle02@gmail.com";

    public String getServiceWriterEmail() {
        return serviceWriterEmail;
    }

    public void setServiceWriterEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.serviceWriterEmail = email.trim();
        }
    }
}