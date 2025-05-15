package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia l'aplicació Spring Boot.
 * L'anotació @SpringBootApplication habilita l'autoconfiguració,
 * l'escaneig de components i altres característiques de Spring Boot.
 */
@SpringBootApplication
public class CalculatorApplication {

    /**
     * Punt d'entrada principal de l'aplicació.
     * @param args Arguments de la línia de comandes (no utilitzats en aquest cas).
     */
    public static void main(String[] args) {
        // Inicia l'aplicació Spring Boot
        SpringApplication.run(CalculatorApplication.class, args);
    }
}
