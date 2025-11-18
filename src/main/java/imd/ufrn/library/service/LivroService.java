package imd.ufrn.library.service;

import imd.ufrn.library.model.Livro;
import imd.ufrn.library.repository.LivroRepository;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro cadastrarLivro(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new ValidationException("O título do livro não pode ser vazio.");
        }

        if(livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new ValidationException("O autor do livro não pode ser vazio.");
        }

        if (livro.getQuantidadeTotal() == null || livro.getQuantidadeTotal() <= 0) {
            throw new ValidationException("A quantidade total de livros deve ser maior que zero.");
        }

        return livroRepository.save(livro);
    }
}
