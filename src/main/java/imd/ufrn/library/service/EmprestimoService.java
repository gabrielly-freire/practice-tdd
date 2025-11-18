package imd.ufrn.library.service;

import imd.ufrn.library.model.Emprestimo;
import imd.ufrn.library.repository.EmprestimoRepository;

import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public Emprestimo cadastrarEmprestimos(Emprestimo emprestimo) {
        return null;
    }

    public Emprestimo cadastrarDevolucao(Long id) {
        return null;
    }
}
