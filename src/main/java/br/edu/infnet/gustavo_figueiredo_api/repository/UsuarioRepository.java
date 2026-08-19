package br.edu.infnet.gustavo_figueiredo_api.repository;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByAtivoTrue ();

    List<Usuario> findByAtivoFalse ();
}
