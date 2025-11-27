package imd.ufrn.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
        @NotBlank(message = "O título do livro não pode ser vazio.") 
        String titulo,
        @NotBlank(message = "O autor do livro não pode ser vazio.") 
        String autor,
        @NotNull(message = "A quantidade total de livros deve ser maior que zero.") 
        @Min(value = 1, message = "A quantidade total de livros deve ser maior que zero.") 
        Integer quantidadeTotal
) {}
