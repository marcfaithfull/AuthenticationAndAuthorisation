package org.example.jws1b.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BlogController {

    @GetMapping("/getname")
    public String getName() {
        return "name";
    }

    @GetMapping("/anothertest")
    public String test() {
        return "test";
    }

}
