package imd.ufrn.library.service;

import imd.ufrn.library.dto.EmprestimoRequest;
import imd.ufrn.library.dto.EmprestimoResponse;
import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.exception.RecursoNaoEncontradoException;
import imd.ufrn.library.mapper.EmprestimoMapper;
import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.model.enums.StatusEmprestimo;
import imd.ufrn.library.repository.EmprestimoRepository;
import imd.ufrn.library.repository.LivroRepository;
import imd.ufrn.library.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, 
            LivroRepository livroRepository, 
            UsuarioRepository usuarioRepository,
            EmprestimoMapper emprestimoMapper) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.emprestimoMapper = emprestimoMapper;
    }

    public Emprestimo emprestar(EmprestimoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        Livro livro = livroRepository.findById(request.livroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado."));

        if (livro.getQuantidadeDisponivel() == null || livro.getQuantidadeDisponivel() <= 0) {
            throw new NegocioException("Não há exemplares disponíveis para empréstimo.");
        }

        long ativos = emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO);
        if (ativos >= 3) {
            throw new NegocioException("Usuário já possui 3 empréstimos ativos.");
        }

        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo(null, livro, usuario, LocalDate.now(), null, StatusEmprestimo.ATIVO);
        return emprestimoRepository.save(emprestimo);
    }

    public EmprestimoResponse emprestarResponse(EmprestimoRequest request) {
        Emprestimo emp = emprestar(request);
        return emprestimoMapper.toResponse(emp);
    }

    public EmprestimoResponse devolver(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado."));
       if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new NegocioException("Empréstimo já está devolvido.");
        }

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);

        Emprestimo salvo = emprestimoRepository.save(emprestimo);
        return emprestimoMapper.toResponse(salvo);
    }

    public List<EmprestimoResponse> listar() {
        return emprestimoMapper.toResponseList(emprestimoRepository.findAll());
    }

    public EmprestimoResponse buscarPorId(Long id) {
        return emprestimoMapper.toResponse(emprestimoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado.")));
    }
}
