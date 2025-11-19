package imd.ufrn.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.model.enums.StatusEmprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    long countByUsuarioIdAndStatus(Long usuarioId, StatusEmprestimo status);
}
