package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.integration.dto.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class EditoraService extends BaseService<Editora> {
    private final EditoraRepository editoraRepository;
    private final CepService cepService;

    public EditoraService (EditoraRepository editoraRepository, CepService cepService) {
        this.editoraRepository = editoraRepository;
        this.cepService = cepService;
    }

    @Override
    protected JpaRepository<Editora, Integer> getRepository () {
        return editoraRepository;
    }

    @Override
    protected Integer obterId (Editora entidade) {
        return entidade.getId();
    }

    @Override
    protected void validarEntidade (Editora entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Editora não pode ser nula.");
        }
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir nome.");
        }
        if (entidade.getCidade() == null || entidade.getCidade().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir cidade.");
        }
        if (entidade.getEmailContato() == null || entidade.getEmailContato().isBlank()) {
            throw new DadosInvalidosException("Editora deve possuir email de contato.");
        }
        if (entidade.getAtiva() == null) {
            throw new DadosInvalidosException("Editora deve informar se está ativa.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Editora";
    }

    @Transactional(readOnly = true)
    public List<Editora> listarAtivas () {
        return editoraRepository.findByAtivaTrue();
    }

    @Transactional(readOnly = true)
    public List<Editora> listarInativas () {
        return editoraRepository.findByAtivaFalse();
    }

    @Transactional(readOnly = true)
    public List<Editora> listarPorAtiva (Boolean ativa) {
        if (ativa == null) {
            return obterLista();
        }
        return ativa ? listarAtivas() : listarInativas();
    }

    @Transactional
    public Editora atualizarCidadePorCep (Integer idEditora, String cep) {
        Editora editora = obterPorId(idEditora);
        ViaCepResponse endereco = cepService.consultar(cep);
        if (endereco.cidade() == null || endereco.cidade().isBlank()) {
            throw new DadosInvalidosException("A API externa não retornou cidade para o CEP informado.");
        }

        editora.setCidade(endereco.cidade());
        return editoraRepository.save(editora);
    }
}
