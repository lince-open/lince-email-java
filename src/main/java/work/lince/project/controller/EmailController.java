package work.lince.Email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import work.lince.commons.log.LogExecutionTime;
import work.lince.project.model.Email;
import work.lince.project.service.EmailService;

@LogExecutionTime
@RestController
@RequestMapping(path = "/emails")
public class EmailController {

    @Autowired
    protected EmailService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Email create(@RequestBody @Validated Email body) {
        return service.create(body);
    }

}