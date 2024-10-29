package org.education.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello, World!";
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> testPost(@RequestBody String body){
        body = "Your message is: " + body.toUpperCase();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}