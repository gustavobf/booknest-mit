package br.edu.infnet.gustavo_figueiredo_api.controller.dto;

import io.swagger.v3.oas.annotations.media.*;

import java.time.*;

@Schema(description = "Payload para registro de devolução de empréstimo.")
public record RegistrarDevolucaoRequest(
        @Schema(description = "Data efetiva da devolução", example = "2026-08-18") LocalDate dataDevolucao,
        @Schema(description = "Valor de multa aplicado na devolução", example = "2.50") Double multa) {
}
