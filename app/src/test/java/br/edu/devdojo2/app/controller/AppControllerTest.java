package br.edu.devdojo2.app.controller;

import br.edu.devdojo2.app.dto.AnimePostReqDto;
import br.edu.devdojo2.app.dto.AnimeRespDto;
import br.edu.devdojo2.app.mapper.AnimeMapper;
import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.service.AnimeService;
import br.edu.devdojo2.app.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

/*O problema de termos a notação @SpringBootTest é que teremos
* o contexto do spring sendo inicializado, ou seja, meio que
* vai tentar startar a aplicação para realizar os testes,
* conectar ao banco de dados da aplicação e assim o teste
* irá falhar. Portanto, usamos a seguinte notação*/
@ExtendWith(SpringExtension.class)
class AppControllerTest {
    /*Essa notação usamos quando queremos testar a classe em si.
    Neste caso, a classe que queremos testar é.*/
    @InjectMocks
    private AppController appController;

    /*Já esta notação serve para testar todas as classes que estão
    sendo utilizadas dentro da classe usada no @InjectMocks*/
    @Mock
    private AnimeService animeServiceMock;

    @Mock
    private AnimeMapper animeMapperMock;

    @BeforeEach
    void setup() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValid()));
        BDDMockito.when(this.animeServiceMock.listAll(any()))
                .thenReturn(animePage);

        BDDMockito.when(this.animeServiceMock.findAll())
                .thenReturn(List.of(AnimeCreator.createAnimeValid()));

        BDDMockito.when(this.animeServiceMock.findByName(anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidDto()));

        BDDMockito.when(this.animeServiceMock.save(any(AnimePostReqDto.class)))
                .thenReturn(AnimeCreator.createAnimeForPost());
    }

    @Test
    @DisplayName("List returns list of Animes inside page object when successfull")
    void list_Returns_ListOfAnimesInsidePageObjectWhenSuccessfull() {
        String expectedName = AnimeCreator.createAnimeValid().getName();
        Page<Anime> animePage = this.appController.findAll(any()).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName().equals(expectedName)).isTrue();
    }

    @Test
    @DisplayName("List returns list of Animes when successfull")
    void listAll_Returns_ListOfAnimesObjectWhenSuccessfull() {
        String expectedName = AnimeCreator.createAnimeValid().getName();
        List<Anime> animes = this.appController.listAll().getBody();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes.get(0).getName().equals(expectedName)).isTrue();
    }

    @Test
    @DisplayName("FindById returns an valid Anime type when successfull")
    void findById_Returns_ListOfAnimesObjectWhenSuccessfull() {
        Long expectedId = AnimeCreator.createAnimeValid().getId();
        Anime anime = this.appController.findById(anyLong()).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getName()).isNotEmpty();
        Assertions.assertThat(anime.getId().equals(expectedId)).isTrue();
    }

    @Test
    @DisplayName("FindByName returns an valid AnimeRespDto type when successfull")
    void findByName_Returns_ListOfAnimesObjectWhenSuccessfull() {
        String expectedName = AnimeCreator.createAnimeValidDto().getName();
        List<AnimeRespDto> respDtos = this.appController.findByName(anyString()).getBody();

        Assertions.assertThat(respDtos).isNotNull();
        Assertions.assertThat(respDtos).isNotEmpty();
        Assertions.assertThat(respDtos.get(0).getName().equals(expectedName)).isTrue();
    }

    @Test
    @DisplayName("Save and returns an valid HttpStatus type when successfull")
    void save_Returns_ListOfAnimesObjectWhenSuccessfull() {
        HttpStatus status = this.appController.saveAnime(any()).getBody();
        Assertions.assertThat(status.equals(HttpStatus.EXPECTATION_FAILED)).isTrue();
    }
}