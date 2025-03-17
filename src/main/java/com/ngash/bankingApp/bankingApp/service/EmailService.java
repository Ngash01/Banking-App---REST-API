package com.ngash.bankingApp.bankingApp.service;

import com.ngash.bankingApp.bankingApp.dto.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
