package com.lucas_monari.customer_manager_api.service;

import com.lucas_monari.customer_manager_api.dto.ScoreResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ScoreIntegrationService {

    private final RestClient restClient;

    public ScoreIntegrationService(@Value("${api.score.url}") String scoreApiUrl) {
        System.out.println("--> URL DO SCORE INJETADA: [" + scoreApiUrl + "]");
        this.restClient = RestClient.builder()
                .baseUrl(scoreApiUrl)
                .build();
    }

    public ScoreResponse getCustomerScore(String cpf) {
        try {
            return restClient.get()
                    .uri("/scores/{cpf}", cpf)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new RuntimeException("Erro ao consultar serviço de score: " + response.getStatusCode());
                    })
                    .body(ScoreResponse.class);

        } catch (RestClientException e) {
            throw new RuntimeException("Serviço de score indisponível no momento.");
        }
    }
}