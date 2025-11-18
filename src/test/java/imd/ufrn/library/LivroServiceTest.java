package imd.ufrn.library;

import imd.ufrn.library.data.LivroData;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.repository.LivroRepository;
import imd.ufrn.library.service.LivroService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso e quantidade disponível igual à quantidade total")
    void deveCadastrarLivroComSucesso() {

        when(livroRepository.save(LivroData.livroValido)).thenReturn(LivroData.livroValido);

        Livro resultado = livroService.cadastrarLivro(LivroData.livroValido);
        assertEquals(LivroData.livroValido, resultado);
        assertEquals(LivroData.livroValido.getQuantidadeTotal(), resultado.getQuantidadeTotal());
        assertEquals(LivroData.livroValido.getQuantidadeTotal(), resultado.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Deve cadastrar livro com quantidade total igual a 1")
    public void deveCadastrarLivroComQuantidadeTotalIgualAUm() {

        when(livroRepository.save(LivroData.livroComQuantidadeTotalIgualAUm)).thenReturn(LivroData.livroComQuantidadeTotalIgualAUm);

        Livro resultado = livroService.cadastrarLivro(LivroData.livroComQuantidadeTotalIgualAUm);

        assertEquals(LivroData.livroComQuantidadeTotalIgualAUm, resultado);
        assertEquals(LivroData.livroComQuantidadeTotalIgualAUm.getQuantidadeTotal(), resultado.getQuantidadeTotal());
        assertEquals(LivroData.livroComQuantidadeTotalIgualAUm.getQuantidadeTotal(), resultado.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com título vazio")
    public void deveLancarExcecaoAoCadastrarLivroComTituloVazio() {

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            livroService.cadastrarLivro(LivroData.livroSemTitulo);
        });

        assertEquals("O título do livro não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com autor vazio")
    public void deveLancarExcecaoAoCadastrarLivroComAutorVazio() {
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            livroService.cadastrarLivro(LivroData.livroSemAutor);
        });

        assertEquals("O autor do livro não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com quantidade total negativa")
    public void deveLancarExcecaoAoCadastrarLivroComQuantidadeTotalNegativa() {

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            livroService.cadastrarLivro(LivroData.livroComQuantidadeNegativa);
        });

        assertEquals("A quantidade total de livros deve ser maior que zero.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar livro com quantidade total igual a zero")
    public void deveLancarExcecaoAoCadastrarLivroComQuantidadeTotalIgualZero() {
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            livroService.cadastrarLivro(LivroData.getLivroComQuantidadeTotalIgualZero);
        });

        assertEquals("A quantidade total de livros deve ser maior que zero.", ex.getMessage());
    }
}
