package fr.univrouen.cv24.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CV24Controller {

    @GetMapping("/")
    @Operation(summary = "Homepage of the application")
    @ResponseStatus(HttpStatus.OK)
    public String index() {
        return "Hello la team, Florian best bébou !!";
    }

}