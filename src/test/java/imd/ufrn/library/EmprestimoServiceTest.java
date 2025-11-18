package imd.ufrn.library;

import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.model.enums.StatusEmprestimo;
import imd.ufrn.library.repository.EmprestimoRepository;
import imd.ufrn.library.repository.LivroRepository;
import imd.ufrn.library.service.EmprestimoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    @DisplayName("Não deve emprestar quando não há exemplares disponíveis")
    void naoDeveEmprestarSemDisponibilidade() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        livro.setQuantidadeDisponivel(0);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            emprestimoService.emprestar(usuario, livro);
        });
        assertEquals("Não há exemplares disponíveis para empréstimo.", ex.getMessage());
    }

    @Test
    @DisplayName("Não deve emprestar quando usuário já possui 3 empréstimos ativos")
    void naoDeveEmprestarComTresAtivos() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 2);
        livro.setQuantidadeDisponivel(2);

        when(emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO))
                .thenReturn(3L);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            emprestimoService.emprestar(usuario, livro);
        });
        assertEquals("Usuário já possui 3 empréstimos ativos.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve emprestar com sucesso: decrementa disponibilidade e cria empréstimo ATIVO")
    void deveEmprestarComSucesso() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 2);
        livro.setQuantidadeDisponivel(2);

        when(emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO))
                .thenReturn(0L);
        when(livroRepository.save(livro)).thenReturn(livro);

        Emprestimo salvo = new Emprestimo(10L, livro, usuario, LocalDate.now(), null, StatusEmprestimo.ATIVO);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(salvo);

        Emprestimo resultado = emprestimoService.emprestar(usuario, livro);

        assertEquals(StatusEmprestimo.ATIVO, resultado.getStatus());
        assertEquals(1, livro.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Não deve devolver se empréstimo já estiver devolvido")
    void naoDeveDevolverEmprestimoJaDevolvido() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        Emprestimo emp = new Emprestimo(10L, livro, usuario, LocalDate.now(), LocalDate.now(), StatusEmprestimo.DEVOLVIDO);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            emprestimoService.devolver(emp);
        });

        assertEquals("Empréstimo já está devolvido.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve devolver com sucesso: status DEVOLVIDO, data preenchida e estoque incrementado")
    void deveDevolverComSucesso() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        livro.setQuantidadeDisponivel(0);
        Emprestimo emp = new Emprestimo(10L, livro, usuario, LocalDate.now(), null, StatusEmprestimo.ATIVO);

        when(livroRepository.save(livro)).thenReturn(livro);
        when(emprestimoRepository.save(emp)).thenReturn(emp);

        Emprestimo resultado = emprestimoService.devolver(emp);

        assertEquals(StatusEmprestimo.DEVOLVIDO, resultado.getStatus());
        assertNotNull(resultado.getDataDevolucao());
        assertEquals(1, livro.getQuantidadeDisponivel());
    }
}
