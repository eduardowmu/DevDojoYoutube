package br.edu.devdojo2.app.integration;

import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.model.AuthUser;
import br.edu.devdojo2.app.repository.AnimeRepository;
import br.edu.devdojo2.app.repository.AuthUserRepository;
import br.edu.devdojo2.app.util.AnimeCreator;
import br.edu.devdojo2.app.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
    /*Assim como este Autowired também podemos duplicar, um para ADMIN
    * e outro para USER, e assim também duplicar para os métodos de cenários
    * de testes*/
    @Autowired
    @Qualifier(value = "testRestTemplateRollAdmin")
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    /*devemos injetar esse repository pois não temos um
    * usuário autenticado*/
    @Autowired
    private AuthUserRepository authUserRepository;

//    @LocalServerPort
//    private int port;

    /*Para que os testes de integração funcionem, é necessário criar
    * esta configuração, ou seja, um Bean onde será utlizdo no momento
    * do @Autowired do tipo TestRestTemplate desta classe*/
    @TestConfiguration
    @Lazy
    static class Config {
        /*Podemos "duplicar" este Bean, e usar este de baixo para tipo ADMIN
        * e o outro para apenas USER*/
        @Bean(name = "testRestTemplateRollUser")
        public TestRestTemplate testRestTemplateRollUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    /*para autorizar o protocolo e host padrão*/
                    .rootUri("http://localhost:".concat(port + ""))
                    .basicAuthentication("edu", "teste");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("List returns list of Animes when successfull")
    void listAll_Returns_ListOfAnimesObjectWhenSuccessfull() {
        Anime animeSaved = this.animeRepository.save(AnimeCreator.createAnimeForPost());

        AuthUser user = AuthUser.builder()
                                .userName("edu")
                                .password("{bcrypt}$2a$10$4Z.MwL.NT.yXJo5PcOuw6uSDHC37.8an8xrQPkzm0yhtNtVRchg42")
                                .authorities("ROLE_USER,ROLE_ADMIN")
                                .build();

        this.authUserRepository.save(user);

        String expectedName = animeSaved.getName();

        //String expectedName = AnimeCreator.createAnimeValid().getName();

        PageableResponse<Anime> animePageableResponse = this.testRestTemplate.exchange(
                                        //lembrar da padronização de proteção de URLs
                "http://localhost:8080".concat("/anime/admin/list"), HttpMethod.GET, null,
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
                "http://localhost:8080/anime/admin".concat(String.valueOf(expectedId)),
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
