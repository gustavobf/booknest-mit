package br.edu.infnet.gustavo_figueiredo_api.repository;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findAllByOrderByNomeAsc ();

    List<Autor> findByNacionalidadeIgnoreCase (String nacionalidade);
}
