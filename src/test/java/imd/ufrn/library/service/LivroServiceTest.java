package imd.ufrn.library.service;

import imd.ufrn.library.dto.LivroRequest;
import imd.ufrn.library.dto.LivroResponse;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.mapper.LivroMapper;
import imd.ufrn.library.repository.LivroRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private LivroService livroService;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso e quantidade disponível igual à quantidade total")
    void deveCadastrarLivroComSucesso() {
        LivroRequest req = new LivroRequest("Livro de Teste", "Autor de Teste", 10);
        Livro salvo = new Livro(1L, req.titulo(), req.autor(), req.quantidadeTotal());
        when(livroMapper.fromRequest(req)).thenReturn(new Livro(null, req.titulo(), req.autor(), req.quantidadeTotal()));
        when(livroRepository.save(any(Livro.class))).thenReturn(salvo);
        when(livroMapper.toResponse(salvo)).thenReturn(new LivroResponse(salvo.getId(), salvo.getTitulo(), salvo.getAutor(), salvo.getQuantidadeTotal(), salvo.getQuantidadeDisponivel()));

        LivroResponse resultado = livroService.cadastrarLivro(req);
        assertEquals(salvo.getId(), resultado.id());
        assertEquals(salvo.getQuantidadeTotal(), resultado.quantidadeTotal());
        assertEquals(salvo.getQuantidadeTotal(), resultado.quantidadeDisponivel());
    }

    @Test
    @DisplayName("Deve cadastrar livro com quantidade total igual a 1")
    public void deveCadastrarLivroComQuantidadeTotalIgualAUm() {
        LivroRequest req = new LivroRequest("Livro de Teste", "Autor de Teste", 1);
        Livro salvo = new Livro(6L, req.titulo(), req.autor(), req.quantidadeTotal());
        when(livroMapper.fromRequest(req)).thenReturn(new Livro(null, req.titulo(), req.autor(), req.quantidadeTotal()));
        when(livroRepository.save(any(Livro.class))).thenReturn(salvo);
        when(livroMapper.toResponse(salvo)).thenReturn(new LivroResponse(salvo.getId(), salvo.getTitulo(), salvo.getAutor(), salvo.getQuantidadeTotal(), salvo.getQuantidadeDisponivel()));

        LivroResponse resultado = livroService.cadastrarLivro(req);

        assertEquals(salvo.getId(), resultado.id());
        assertEquals(salvo.getQuantidadeTotal(), resultado.quantidadeTotal());
        assertEquals(salvo.getQuantidadeTotal(), resultado.quantidadeDisponivel());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com título vazio")
    public void deveLancarExcecaoAoCadastrarLivroComTituloVazio() {
        LivroRequest livroSemTitulo = new LivroRequest("", "Autor de Teste", 5);
        ValidationException ex = assertThrows(ValidationException.class, () -> livroService.cadastrarLivro(livroSemTitulo));

        assertEquals("O título do livro não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com autor vazio")
    public void deveLancarExcecaoAoCadastrarLivroComAutorVazio() {
        LivroRequest livroSemAutor = new LivroRequest("Livro de Teste", "", 5);
        ValidationException ex = assertThrows(ValidationException.class, () -> livroService.cadastrarLivro(livroSemAutor));

        assertEquals("O autor do livro não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com quantidade total negativa")
    public void deveLancarExcecaoAoCadastrarLivroComQuantidadeTotalNegativa() {
        LivroRequest livroQuantidadeNegativa = new LivroRequest("Livro de Teste", "Autor de Teste", -1);
        ValidationException ex = assertThrows(ValidationException.class, () -> livroService.cadastrarLivro(livroQuantidadeNegativa));

        assertEquals("A quantidade total de livros deve ser maior que zero.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com quantidade total igual a zero")
    public void deveLancarExcecaoAoCadastrarLivroComQuantidadeTotalIgualZero() {
        LivroRequest livroQuantidadeZero = new LivroRequest("Livro de Teste", "Autor de Teste", 0);
        ValidationException ex = assertThrows(ValidationException.class, () -> livroService.cadastrarLivro(livroQuantidadeZero));

        assertEquals("A quantidade total de livros deve ser maior que zero.", ex.getMessage());
    }
}
