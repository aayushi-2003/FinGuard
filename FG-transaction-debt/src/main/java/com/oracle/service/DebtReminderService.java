package com.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.oracle.model.Debt;
import com.oracle.model.Mail;
import com.oracle.repository.DebtRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class DebtReminderService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDebtReminders() {
        LocalDate today = LocalDate.now();
        List<Debt> debts = debtRepository.findByDueDateAndReminded(today, false);

        for (Debt debt : debts) {
            Mail mail = new Mail();
            mail.setTo(debt.getEmail());
            mail.setSubject("Debt Payment Due");
            mail.setBody("Dear user, your debt payment of ₹" + debt.getPrincipleAmt()
                + " is due today. Please pay to avoid penalty.");
            sendEmail(mail);

            debt.setReminded(true);
            debtRepository.save(debt);
        }
    }

    public void sendEmail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());
        mailSender.send(message);
    }
}