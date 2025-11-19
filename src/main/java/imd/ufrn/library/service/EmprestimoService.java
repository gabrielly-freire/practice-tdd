package imd.ufrn.library.service;

import imd.ufrn.library.exception.NegocioException;
import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.model.Livro;
import imd.ufrn.library.model.Usuario;
import imd.ufrn.library.model.enums.StatusEmprestimo;
import imd.ufrn.library.repository.EmprestimoRepository;
import imd.ufrn.library.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    public Emprestimo emprestar(Usuario usuario, Livro livro) {

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

    public Emprestimo devolver(Emprestimo emprestimo) {

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new NegocioException("Empréstimo já está devolvido.");
        }

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }
}
