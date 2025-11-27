package imd.ufrn.library.service;

import imd.ufrn.library.dto.LivroRequest;
import imd.ufrn.library.dto.LivroResponse;
import imd.ufrn.library.exception.RecursoNaoEncontradoException;
import imd.ufrn.library.mapper.LivroMapper;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.repository.LivroRepository;
import jakarta.validation.ValidationException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroService(LivroRepository livroRepository, LivroMapper livroMapper) {
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
    }

    public LivroResponse cadastrarLivro(LivroRequest request) {
        if (request.titulo() == null || request.titulo().trim().isEmpty()) {
            throw new ValidationException("O título do livro não pode ser vazio.");
        }
        if (request.autor() == null || request.autor().trim().isEmpty()) {
            throw new ValidationException("O autor do livro não pode ser vazio.");
        }
        if (request.quantidadeTotal() == null || request.quantidadeTotal() <= 0) {
            throw new ValidationException("A quantidade total de livros deve ser maior que zero.");
        }

        Livro livro = livroMapper.fromRequest(request);
        return livroMapper.toResponse(livroRepository.save(livro));
    }

    public List<LivroResponse> listar() {
        return livroMapper.toResponseList(livroRepository.findAll());
    }

    public LivroResponse buscarPorId(Long id) {
        return livroMapper.toResponse(livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado.")));
    }

    public LivroResponse cadastrar(LivroRequest request) {
        return cadastrarLivro(request);
    }

    public List<LivroResponse> listarResponses() {
        return listar();
    }

    public LivroResponse buscarResponse(Long id) {
        return buscarPorId(id);
    }
}
