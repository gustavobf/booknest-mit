package br.edu.infnet.gustavo_figueiredo_api.repository;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("select c from Categoria c left join c.livros l group by c.id order by count(l) desc")
    List<Categoria> findAllOrderByQuantidadeLivrosDesc ();
}
