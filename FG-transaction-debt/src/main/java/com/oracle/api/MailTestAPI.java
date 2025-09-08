package com.oracle.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.oracle.model.Mail;
import com.oracle.service.DebtReminderService;

@RestController
@RequestMapping("/mail") // This will make your endpoint available at /mail/send
public class MailTestAPI {

    @Autowired
    private DebtReminderService debtReminderService;

    @PostMapping("/send")
    public String sendReminderMail( @RequestBody Mail mail
    ) {
        try {
            debtReminderService.sendEmail(mail);
            return "Mail sent  " ;
        } catch (Exception e) {
            e.printStackTrace();
            return "Mail failed: " + e.getMessage();
        }
    }
}