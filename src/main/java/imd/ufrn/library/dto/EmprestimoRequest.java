package imd.ufrn.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EmprestimoRequest(
        @NotNull(message = "Usuário não informado.") @Min(value = 1, message = "Usuário inválido.") 
        Long usuarioId,
        @NotNull(message = "Livro não informado.") @Min(value = 1, message = "Livro inválido.") 
        Long livroId
) {}
