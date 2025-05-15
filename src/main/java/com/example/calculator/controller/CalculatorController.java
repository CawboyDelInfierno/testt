package com.example.calculator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map; // Importa Map per crear la resposta JSON

/**
 * Controlador REST que gestiona les operacions de la calculadora.
 * Defineix els endpoints per a suma, resta, multiplicació i divisió.
 */
@RestController
@RequestMapping("/calculator") // Ruta base per a tots els endpoints d'aquest controlador
public class CalculatorController {

    /**
     * Endpoint per sumar dos nombres.
     * @param a El primer operand.
     * @param b El segon operand.
     * @return ResponseEntity amb el resultat de la suma en format JSON.
     */
    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestParam double a, @RequestParam double b) {
        double result = a + b;
        // Retorna un Map per tenir una estructura JSON consistent
        Map<String, Object> response = Map.of(
            "operation", "add",
            "a", a,
            "b", b,
            "result", result
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resta")
    public ResponseEntity<Map<String, Object>> rest(@RequestParam double a, @RequestParam double b) {
        double result = a - b;
        Map<String, Object> response = Map.of(
            "operation", "resta",
            "a", a,
            "b", b,
            "result", result
        );
        return ResponseEntity.ok(response);
    }

  
}
