package imd.ufrn.library.service;

import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.dto.EmprestimoRequest;
import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.model.enums.StatusEmprestimo;
import imd.ufrn.library.repository.EmprestimoRepository;
import imd.ufrn.library.repository.LivroRepository;
import imd.ufrn.library.repository.UsuarioRepository;
import imd.ufrn.library.mapper.EmprestimoMapper;
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

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmprestimoMapper emprestimoMapper;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    @DisplayName("Não deve emprestar quando não há exemplares disponíveis")
    void naoDeveEmprestarSemDisponibilidade() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        livro.setQuantidadeDisponivel(0);

        EmprestimoRequest req = new EmprestimoRequest(usuario.getId(), livro.getId());
        when(usuarioRepository.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        when(livroRepository.findById(livro.getId())).thenReturn(java.util.Optional.of(livro));

        NegocioException ex = assertThrows(NegocioException.class, () -> emprestimoService.emprestar(req));
        assertEquals("Não há exemplares disponíveis para empréstimo.", ex.getMessage());
    }

    @Test
    @DisplayName("Não deve emprestar quando usuário já possui 3 empréstimos ativos")
    void naoDeveEmprestarComTresAtivos() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 2);
        livro.setQuantidadeDisponivel(2);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        when(livroRepository.findById(livro.getId())).thenReturn(java.util.Optional.of(livro));
        when(emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO))
                .thenReturn(3L);

        EmprestimoRequest req = new EmprestimoRequest(usuario.getId(), livro.getId());
        NegocioException ex = assertThrows(NegocioException.class, () -> emprestimoService.emprestar(req));
        assertEquals("Usuário já possui 3 empréstimos ativos.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve emprestar com sucesso: decrementa disponibilidade e cria empréstimo ATIVO")
    void deveEmprestarComSucesso() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 2);
        livro.setQuantidadeDisponivel(2);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        when(livroRepository.findById(livro.getId())).thenReturn(java.util.Optional.of(livro));
        when(emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO))
                .thenReturn(0L);
        when(livroRepository.save(livro)).thenReturn(livro);

        Emprestimo salvo = new Emprestimo(10L, livro, usuario, LocalDate.now(), null, StatusEmprestimo.ATIVO);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(salvo);

        EmprestimoRequest req = new EmprestimoRequest(usuario.getId(), livro.getId());
        Emprestimo resultado = emprestimoService.emprestar(req);

        assertEquals(StatusEmprestimo.ATIVO, resultado.getStatus());
        assertEquals(1, livro.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Não deve devolver se empréstimo já estiver devolvido")
    void naoDeveDevolverEmprestimoJaDevolvido() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        Emprestimo emp = new Emprestimo(10L, livro, usuario, LocalDate.now(), LocalDate.now(), StatusEmprestimo.DEVOLVIDO);

        when(emprestimoRepository.findById(emp.getId())).thenReturn(java.util.Optional.of(emp));
        NegocioException ex = assertThrows(NegocioException.class, () -> emprestimoService.devolver(emp.getId()));

        assertEquals("Empréstimo já está devolvido.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve devolver com sucesso: status DEVOLVIDO, data preenchida e estoque incrementado")
    void deveDevolverComSucesso() {
        Usuario usuario = new Usuario(1L, "Usuário", "user@email.com");
        Livro livro = new Livro(1L, "Título", "Autor", 1);
        livro.setQuantidadeDisponivel(0);
        Emprestimo emp = new Emprestimo(10L, livro, usuario, LocalDate.now(), null, StatusEmprestimo.ATIVO);

        when(emprestimoRepository.findById(emp.getId())).thenReturn(java.util.Optional.of(emp));
        when(livroRepository.save(livro)).thenReturn(livro);
        when(emprestimoRepository.save(emp)).thenReturn(emp);
        when(emprestimoMapper.toResponse(emp)).thenReturn(new imd.ufrn.library.dto.EmprestimoResponse(
                emp.getId(),
                emp.getLivro().getId(),
                emp.getUsuario().getId(),
                emp.getDataEmprestimo(),
                LocalDate.now(),
                StatusEmprestimo.DEVOLVIDO
        ));

        imd.ufrn.library.dto.EmprestimoResponse resultado = emprestimoService.devolver(emp.getId());

        assertEquals(StatusEmprestimo.DEVOLVIDO, resultado.status());
        assertNotNull(resultado.dataDevolucao());
        assertEquals(1, livro.getQuantidadeDisponivel());
    }
}
