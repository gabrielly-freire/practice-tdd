package imd.ufrn.library.mapper;

import imd.ufrn.library.dto.EmprestimoResponse;
import imd.ufrn.library.model.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {
    @Mapping(target = "livroId", source = "livro.id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    EmprestimoResponse toResponse(Emprestimo emprestimo);
    List<EmprestimoResponse> toResponseList(List<Emprestimo> emprestimos);
}
