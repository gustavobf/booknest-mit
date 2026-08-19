package br.edu.infnet.gustavo_figueiredo_api.repository;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface EditoraRepository extends JpaRepository<Editora, Integer> {
    List<Editora> findByAtivaTrue ();

    List<Editora> findByAtivaFalse ();
}
