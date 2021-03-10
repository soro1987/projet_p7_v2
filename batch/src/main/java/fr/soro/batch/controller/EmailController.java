package fr.soro.batch.controller;

import fr.soro.batch.modele.EmailTemplate;
import fr.soro.batch.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "sendEmail")
    public String emailSender(@RequestBody EmailTemplate content){
        emailService.sendTextEmail(content);
        return "Email send";
    }
}
