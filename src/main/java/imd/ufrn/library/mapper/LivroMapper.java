package imd.ufrn.library.mapper;

import imd.ufrn.library.dto.LivroRequest;
import imd.ufrn.library.dto.LivroResponse;
import imd.ufrn.library.model.Livro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    LivroResponse toResponse(Livro livro);
    List<LivroResponse> toResponseList(List<Livro> livros);
    Livro fromRequest(LivroRequest request);
}
