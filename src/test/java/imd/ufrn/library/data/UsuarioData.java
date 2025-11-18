package imd.ufrn.library.data;

import imd.ufrn.library.model.Usuario;

public class UsuarioData {

    public static Usuario joaoSilvaEmailValido = new Usuario(1L, "João Silva", "joao@email.com");

    public static Usuario semNomeEmailValido = new Usuario(2L, "", "joao@email.com");

    public static Usuario semEmailInvalidoValido = new Usuario(3L, "João Silva", "joaoemail.com");

    public static Usuario mariaOliveiraSemEmail = new Usuario(4L, "Maria Oliveira", "");

}
