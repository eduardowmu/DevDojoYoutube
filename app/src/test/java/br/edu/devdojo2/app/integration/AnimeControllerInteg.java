package br.edu.devdojo2.app.integration;

import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.repository.AnimeRepository;
import br.edu.devdojo2.app.util.AnimeCreator;
import br.edu.devdojo2.app.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

/*Iremos rodar a aplicação em uma porta aleatória para não causar
* conflito, caso já tenha uma aplicação sendo executada na porta
* padrão.*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AnimeControllerInteg {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("List returns list of Animes when successfull")
    void listAll_Returns_ListOfAnimesObjectWhenSuccessfull() {
        /*Anime animeSaved = this.animeRepository.save(AnimeCreator.createAnimeForPost());

        String expectedName = animeSaved.getName();*/

        String expectedName = AnimeCreator.createAnimeValid().getName();

        PageableResponse<Anime> animePageableResponse = this.testRestTemplate.exchange(
                "http://localhost:8080".concat("/anime/list"), HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {}).getBody();

        Assertions.assertThat(animePageableResponse).isNotNull();

        Assertions.assertThat(animePageableResponse.toList()).isNotEmpty();

        Assertions.assertThat(animePageableResponse.toList().get(0).getName()
                .equals(expectedName)).isTrue();
    }

    @Test
    @DisplayName("FindById returns an valid Anime type when successfull")
    void findById_Returns_ListOfAnimesObjectWhenSuccessfull() {
        Long expectedId = AnimeCreator.createAnimeValid().getId();

        Anime anime = this.testRestTemplate.exchange(
                "http://localhost:8080/anime/".concat(String.valueOf(expectedId)),
                HttpMethod.GET, null, Anime.class).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getName()).isNotEmpty();
        Assertions.assertThat(anime.getId().equals(expectedId)).isTrue();
    }

    /*
    @Test
    @DisplayName("FindByName returns an valid AnimeRespDto type when successfull")
    void findByName_Returns_ListOfAnimesObjectWhenSuccessfull() {
        String expectedName = AnimeCreator.createAnimeValidDto().getName();
        List<AnimeRespDto> respDtos = null;//this.appController.findByName(anyString()).getBody();

        Assertions.assertThat(respDtos).isNotNull();
        Assertions.assertThat(respDtos).isNotEmpty();
        Assertions.assertThat(respDtos.get(0).getName().equals(expectedName)).isTrue();
    }

    @Test
    @DisplayName("Save and returns an valid HttpStatus type when successfull")
    void save_Returns_ListOfAnimesObjectWhenSuccessfull() {
        HttpStatus status = null;//this.appController.saveAnime(any()).getBody();
        Assertions.assertThat(status.equals(HttpStatus.EXPECTATION_FAILED)).isTrue();
    }

    */
}
