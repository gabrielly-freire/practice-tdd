package imd.ufrn.library.repository;

import imd.ufrn.library.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    boolean existsUsuarioByEmail(String email);
}
