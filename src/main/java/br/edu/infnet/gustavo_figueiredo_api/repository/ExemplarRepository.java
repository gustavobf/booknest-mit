package br.edu.infnet.gustavo_figueiredo_api.repository;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {
    List<Exemplar> findByDisponivelTrue ();

    List<Exemplar> findByDisponivelFalse ();

    List<Exemplar> findByLivroId (Integer idLivro);
}
