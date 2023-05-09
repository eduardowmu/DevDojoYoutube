package br.edu.devdojo2.app.util;

import br.edu.devdojo2.app.dto.AnimePostReqDto;
import br.edu.devdojo2.app.dto.AnimeRespDto;
import br.edu.devdojo2.app.model.Anime;
import net.bytebuddy.dynamic.DynamicType;

import java.util.Optional;

/*Classe onde obteremos todos os objetos que usaremos em
* nossas classes de testes*/
public class AnimeCreator {
    public static Anime createAnimeForPost() {
        Anime anime = new Anime();
        anime.setName("As aventuras de Chichiro");
        return anime;
    }

    public static AnimePostReqDto createAnimePostReqDtoForPost() {
        AnimePostReqDto anime = new AnimePostReqDto();
        anime.setName("As aventuras de Chichiro");
        return anime;
    }

    public static Anime createAnimeValid() {
        Anime anime = new Anime();
        anime.setId(1L);
        anime.setName("Yuyu Hakusho");
        return anime;
    }

    public static AnimeRespDto createAnimeValidDto() {
        AnimeRespDto respDto = new AnimeRespDto();
        respDto.setName("Yuyu Hakusho");
        return respDto;
    }

    public static Anime updatedAnimeExisted() {
        Anime anime = new Anime();
        anime.setId(1L);
        anime.setName("Digimon");
        return anime;
    }
}
