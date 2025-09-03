package com.oracle.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.oracle.model.Debt;
import com.oracle.repository.DebtRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtReminderService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 8 * * *") // every day at 8 AM
    public void sendDebtReminders() {
        LocalDate today = LocalDate.now();
        List<Debt> debts = debtRepository.findByDueDateAndReminded(today, false);

        for (Debt debt : debts) {
            sendEmail(debt.getEmail(),
                "Debt Payment Due",
                "Dear user, your debt payment of ₹" + debt.getPrincipleAmt() +
                " is due today. Please pay to avoid penalty.");

            debt.setReminded(true);
            debtRepository.save(debt);
        }
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
    }

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}