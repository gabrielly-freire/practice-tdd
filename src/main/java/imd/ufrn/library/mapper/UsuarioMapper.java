package imd.ufrn.library.mapper;

import imd.ufrn.library.dto.UsuarioRequest;
import imd.ufrn.library.dto.UsuarioResponse;
import imd.ufrn.library.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponse toResponse(Usuario usuario);
    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);
    Usuario fromRequest(UsuarioRequest request);
}
