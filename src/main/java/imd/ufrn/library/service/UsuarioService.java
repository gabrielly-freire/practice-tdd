package imd.ufrn.library.service;

import imd.ufrn.library.dto.UsuarioRequest;
import imd.ufrn.library.dto.UsuarioResponse;
import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.exception.RecursoNaoEncontradoException;
import imd.ufrn.library.mapper.UsuarioMapper;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.repository.UsuarioRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse cadastrarUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsUsuarioByEmail(request.email())) {
            throw new NegocioException("Já existe um usuário cadastrado com o email: " + request.email());
        }
        Usuario usuario = usuarioMapper.fromRequest(request);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponse> listar() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse buscarPorId(Long id) {
        return usuarioMapper.toResponse(usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado.")));
    }
}
