package imd.ufrn.library.service;

import imd.ufrn.library.dto.UsuarioRequest;
import imd.ufrn.library.dto.UsuarioResponse;
import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.repository.UsuarioRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import imd.ufrn.library.mapper.UsuarioMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso")
    public void deveCadastrarUsuarioComSucesso() {
        UsuarioRequest req = new UsuarioRequest("João Silva", "joao@email.com");
        Usuario salvo = new Usuario(1L, req.nome(), req.email());
        when(usuarioMapper.fromRequest(req)).thenReturn(new Usuario(null, req.nome(), req.email()));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(salvo);
        when(usuarioMapper.toResponse(salvo)).thenReturn(new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail()));

        UsuarioResponse resultado = usuarioService.cadastrarUsuario(req);
        assertEquals(salvo.getId(), resultado.id());
        assertEquals(salvo.getNome(), resultado.nome());
        assertEquals(salvo.getEmail(), resultado.email());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com email já existente")
    public void deveLancarExcecaoAoCadastrarUsuarioComEmailExistente() {
        UsuarioRequest req = new UsuarioRequest("João Silva", "joao@email.com");
        when(usuarioRepository.existsUsuarioByEmail(req.email())).thenReturn(true);

        NegocioException ex = assertThrows(NegocioException.class, () -> usuarioService.cadastrarUsuario(req));
        assertEquals("Já existe um usuário cadastrado com o email: " + req.email(), ex.getMessage());
    }
}
