package imd.ufrn.library.controller;

import imd.ufrn.library.dto.LivroResponse;
import imd.ufrn.library.dto.LivroRequest;
import jakarta.validation.Valid;
import imd.ufrn.library.service.LivroService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@RequestBody @Valid LivroRequest request) {
    
        return ResponseEntity.ok(livroService.cadastrarLivro(request));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> listar() {
        return ResponseEntity.ok(livroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }
}
