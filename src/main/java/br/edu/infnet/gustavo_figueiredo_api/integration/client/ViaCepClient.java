package br.edu.infnet.gustavo_figueiredo_api.integration.client;

import br.edu.infnet.gustavo_figueiredo_api.integration.dto.*;
import feign.*;

public interface ViaCepClient {
    @RequestLine("GET /ws/{cep}/json/")
    ViaCepResponse consultarCep (@Param("cep") String cep);
}
