package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail, EmailTemplate emailTemplate) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail, emailTemplate));
            if (emailTemplate == EmailTemplate.TRELLO_CARD_MAIL) {
                LOGGER.info("Email confirming that new Trello Card has been sent successfully.");
            } else if (emailTemplate == EmailTemplate.SCHEDULED_MAIL) {
                LOGGER.info("Scheduled email about current quantity of tasks has been sent successfully.");
            }
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail, EmailTemplate emailTemplate){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());

            if (emailTemplate == EmailTemplate.TRELLO_CARD_MAIL) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            } else if (emailTemplate == EmailTemplate.SCHEDULED_MAIL) {
                messageHelper.setText (mailCreatorService.buildScheduledEmail(mail.getMessage()), true);
            }
        };
    }
    private SimpleMailMessage createMailMessage(final Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (mail.getToCc()!=null && !mail.getToCc().equals("")){
            mailMessage.setCc(mail.getToCc());
            LOGGER.info("CC has been included");
        }
        return mailMessage;
    }
}
