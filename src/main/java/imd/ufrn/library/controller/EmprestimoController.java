package imd.ufrn.library.controller;

import imd.ufrn.library.dto.EmprestimoResponse;
import imd.ufrn.library.dto.EmprestimoRequest;
import jakarta.validation.Valid;
import imd.ufrn.library.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponse> emprestar(@RequestBody @Valid EmprestimoRequest request) {
        return ResponseEntity.ok(emprestimoService.emprestarResponse(request));
    }

    @PostMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoResponse> devolver(@PathVariable Long id) {
        EmprestimoResponse devolvido = emprestimoService.devolver(id);
        return ResponseEntity.ok(devolvido);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponse>> listar() {
        return ResponseEntity.ok(emprestimoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }
}
