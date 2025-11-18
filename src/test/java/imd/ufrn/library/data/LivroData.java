package imd.ufrn.library.data;

import imd.ufrn.library.model.Livro;

public class LivroData {

    public static Livro livroValido = new Livro(1L, "Livro de Teste", "Autor de Teste", 10);
    public static Livro livroComQuantidadeTotalIgualAUm = new Livro(6L, "Livro de Teste", "Autor de Teste", 1);
    public static Livro livroSemAutor = new Livro(2L, "Livro de Teste", "", 5);
    public static Livro livroSemTitulo = new Livro(3L, "", "Autor de Teste", 5);
    public static Livro livroComQuantidadeNegativa = new Livro(4L, "Livro de Teste", "Autor de Teste", -1);
    public static Livro getLivroComQuantidadeTotalIgualZero = new Livro(5L, "Livro de Teste", "Autor de Teste", 0);

}
