package imd.ufrn.library.dto;

public record LivroResponse(Long id, 
    String titulo, 
    String autor, 
    Integer quantidadeTotal, 
    Integer quantidadeDisponivel) {}
