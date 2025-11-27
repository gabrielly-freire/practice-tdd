package imd.ufrn.library.dto;

import imd.ufrn.library.model.enums.StatusEmprestimo;
import java.time.LocalDate;

public record EmprestimoResponse(Long id, 
    Long livroId,
    Long usuarioId, 
    LocalDate dataEmprestimo, 
    LocalDate dataDevolucao, 
    StatusEmprestimo status) {}
