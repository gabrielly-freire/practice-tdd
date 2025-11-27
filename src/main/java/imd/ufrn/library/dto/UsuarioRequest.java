package imd.ufrn.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank(message = "O nome do usuário não pode ser vazio.") 
        String nome,
        @NotBlank(message = "O email do usuário não pode ser vazio.")
        @Email(message = "O email do usuário é inválido.") 
        String email
) {}
