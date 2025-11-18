package imd.ufrn.library.service;

import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.exception.ValidacaoException;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsUsuarioByEmail(usuario.getEmail())) {
            throw new NegocioException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new ValidationException("O nome do usuário não pode ser vazio.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new ValidationException("O email do usuário não pode ser vazio.");
        }

        if (usuario.getEmail() != null && !usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("O email do usuário é inválido.");
        }

        return usuarioRepository.save(usuario);
    }
}
