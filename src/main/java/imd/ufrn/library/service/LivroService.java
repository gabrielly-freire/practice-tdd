package imd.ufrn.library.service;

import imd.ufrn.library.model.Livro;
import imd.ufrn.library.repository.LivroRepository;

import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void cadastrarLivros(Livro livro) {

    }
}
