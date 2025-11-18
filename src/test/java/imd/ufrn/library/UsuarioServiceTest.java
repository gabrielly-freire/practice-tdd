package imd.ufrn.library;

import imd.ufrn.library.data.UsuarioData;
import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.repository.UsuarioRepository;
import imd.ufrn.library.service.UsuarioService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso")
    public void deveCadastrarUsuarioComSucesso() {
        Usuario resultado = usuarioService.cadastrarUsuario(UsuarioData.joaoSilvaEmailValido);

        assertEquals(UsuarioData.joaoSilvaEmailValido, resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com email já existente")
    public void deveLancarExcecaoAoCadastrarUsuarioComEmailExistente() {

        when(usuarioRepository.existsUsuarioByEmail(UsuarioData.joaoSilvaEmailValido.getEmail()))
            .thenReturn(true);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            usuarioService.cadastrarUsuario(UsuarioData.joaoSilvaEmailValido);
        });

        assertEquals("Já existe um usuário cadastrado com o email: " + UsuarioData.joaoSilvaEmailValido.getEmail(), ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com nome vazio")
    public void deveLancarExcecaoAoCadastrarUsuarioComNomeVazio() {

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            usuarioService.cadastrarUsuario(UsuarioData.semNomeEmailValido);
        });

        assertEquals("O nome do usuário não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um usuario com email vazio")
    public void deveLancarExcecaoAoCadastrarUsuarioComEmailVazio() {

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            usuarioService.cadastrarUsuario(UsuarioData.mariaOliveiraSemEmail);
        });

        assertEquals("O email do usuário não pode ser vazio.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um usuario com email inválido")
    public void deveLancarExcecaoAoCadastrarUsuarioComEmailInvalido() {

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            usuarioService.cadastrarUsuario(UsuarioData.semEmailInvalidoValido);
        });

        assertEquals("O email do usuário é inválido.", ex.getMessage());
    }
}
