package com.project.mypocketpurse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class GenericController<T> {
    abstract ResponseEntity<T> getOne(@RequestParam(value = "id") Long id);
}