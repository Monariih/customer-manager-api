package com.lucas_monari.customer_manager_api.dto;

public record ScoreResponse(
        String cpf,
        Integer score,
        String classification
) {
}