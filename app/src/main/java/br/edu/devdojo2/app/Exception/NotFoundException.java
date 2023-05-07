package br.edu.devdojo2.app.Exception;

import br.edu.devdojo2.app.dto.AnimeGetRespBody;
import br.edu.devdojo2.app.model.Anime;

public class NotFoundException {
    public static Anime notFoundException(Anime anime) {
        return getEmptyAnime(anime);
    }

    private static Anime getEmptyAnime(Anime anime) {
        anime.setId(0L);
        anime.setName("Anime nao encontrado");
        return anime;
    }
}