package com.lucas_monari.customer_manager_api.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.lucas_monari.customer_manager_api.dto.ScoreResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class ScoreIntegrationServiceTest {

    @Autowired
    private ScoreIntegrationService scoreIntegrationService;

    @Test
    void getCustomerScore_ShouldReturnScoreSuccessfully() {
        String testCpf = "12345678901";
        stubFor(get("/scores/" + testCpf)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "cpf": "12345678901",
                                    "score": 800,
                                    "classification": "LOW_RISK"
                                }
                                """)));

        ScoreResponse response = scoreIntegrationService.getCustomerScore(testCpf);

        assertNotNull(response);
        assertEquals(testCpf, response.cpf());
        assertEquals(800, response.score());
    }
}