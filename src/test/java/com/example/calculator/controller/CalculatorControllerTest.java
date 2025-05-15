package com.example.calculator.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions; // Importa ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Per comprovar JSON

/**
 * Tests d'integració per a CalculatorController.
 * Utilitza MockMvc per simular peticions HTTP sense necessitat d'aixecar un servidor real.
 */
@SpringBootTest // Carrega el context complet de Spring Boot per a tests d'integració
@AutoConfigureMockMvc // Configura automàticament MockMvc
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc; // Injecta MockMvc per fer les peticions simulades

    // --- Tests per a l'endpoint /add ---

    @Test
    void testAddEndpoint_Success() throws Exception {
        performGetRequest("/calculator/add", "5", "3")
                .andExpect(status().isOk()) // Espera 200 OK
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.a").value(5.0))
                .andExpect(jsonPath("$.b").value(3.0))
                .andExpect(jsonPath("$.result").value(8.0)); // Comprova el resultat
    }

    @Test
    void testAddWithNegativeNumbers() throws Exception {
         performGetRequest("/calculator/add", "-5", "3")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-2.0)); // Comprova resultat amb negatius
    }

    @Test
    void testAddWithZero() throws Exception {
         performGetRequest("/calculator/add", "10", "0")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(10.0)); // Comprova suma amb zero
    }
    
    // --- Tests per a l'endpoint /resta ---
    
    @Test
    void testRestaEndpoint_Success() throws Exception {
        performGetRequest("/calculator/resta", "10", "3")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("resta"))
                .andExpect(jsonPath("$.a").value(10.0))
                .andExpect(jsonPath("$.b").value(3.0))
                .andExpect(jsonPath("$.result").value(7.0));
    }

    @Test
    void testRestaWithNegativeNumbers() throws Exception {
        performGetRequest("/calculator/resta", "-5", "3")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-8.0));
    }

    @Test
    void testRestaWithZero() throws Exception {
        performGetRequest("/calculator/resta", "10", "0")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(10.0));
    }

    @Test
    void testRestaResultingNegative() throws Exception {
        performGetRequest("/calculator/resta", "5", "8")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-3.0));
    }

    // Versió parametritzada
    @ParameterizedTest
    @CsvSource({
        "10, 3, 7.0",
        "5, 8, -3.0",
        "-5, -3, -2.0",
        "10, 0, 10.0",
        "0, 5, -5.0",
        "7.5, 2.5, 5.0"
    })
    void testRestaEndpoint_Parametrized(String a, String b, double expectedResult) throws Exception {
        performGetRequest("/calculator/resta", a, b)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("resta"))
                .andExpect(jsonPath("$.a").value(Double.parseDouble(a)))
                .andExpect(jsonPath("$.b").value(Double.parseDouble(b)))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

/* EXEMPLE USANT VARIS PARAMETRES
    // --- Tests per a l'endpoint /subtract ---

    @ParameterizedTest // Test parametritzat per cobrir diversos casos
    @CsvSource({ // Font de dades CSV: a, b, expectedResult
            "10, 3, 7.0",
            "5, 8, -3.0",
            "-5, -3, -2.0",
            "10, 0, 10.0",
            "0, 5, -5.0"
    })
    void testSubtractEndpoint(String a, String b, double expectedResult) throws Exception {
        performGetRequest("/calculator/subtract", a, b)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtract"))
                .andExpect(jsonPath("$.a").value(Double.parseDouble(a))) // Converteix a double per comparar
                .andExpect(jsonPath("$.b").value(Double.parseDouble(b)))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

*/
    /**
     * Mètode auxiliar per realitzar una petició GET i retornar el ResultActions.
     * Això evita la repetició de codi en els tests.
     *
     * @param path L'endpoint a provar (ex: "/calculator/add").
     * @param a El valor del paràmetre 'a'.
     * @param b El valor del paràmetre 'b'.
     * @return El ResultActions per poder encadenar expectatives (.andExpect(...)).
     * @throws Exception Si hi ha algun error durant la petició MockMvc.
     */
    private ResultActions performGetRequest(String path, String a, String b) throws Exception {
        return mockMvc.perform(get(path) // Fes un GET a la ruta especificada
                .param("a", a)        // Amb paràmetre 'a'
                .param("b", b));      // Amb paràmetre 'b'
    }
}
