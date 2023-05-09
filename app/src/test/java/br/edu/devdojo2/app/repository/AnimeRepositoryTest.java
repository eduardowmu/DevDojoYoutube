package br.edu.devdojo2.app.repository;

import br.edu.devdojo2.app.model.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/*Para criar essa classe de test de base de dados, basta
* entrar na interface, no caso, AnimeRepository, e em cima
* da classe digitar ALT + ENTER*/
@DataJpaTest
@DisplayName("Testes para o repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    private Anime animeTest, animeSaved;

    @BeforeEach
    void setup() {
        animeTest = createAnime();
        animeSaved = this.animeRepository.save(animeTest);
    }

    @Test
    @DisplayName("Save Anime when successfull")
    void saveAnimeSucessfull() {
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName().equals(animeTest.getName())).isTrue();
    }

    @Test
    @DisplayName("Update Anime when successfull")
    void updatedAnimeSucessfull() {
        animeSaved.setName("Huck");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeSaved.getId().equals(animeUpdated.getId()));
        Assertions.assertThat(animeUpdated.getName().equals(animeTest.getName())).isFalse();
    }

    @Test
    @DisplayName("Deleted Anime when successfull")
    void deletedAnimeSucessfull() {
        Long id = animeSaved.getId();

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeDeleted = this.animeRepository.findById(id);

        Assertions.assertThat(animeDeleted.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find Anime by name when successfull")
    void findByNameSucessfull() {
        List<Anime> animes = this.animeRepository.findByName(this.animeTest.getName());

        Boolean existe = this.existeAnimeName(animes);

        Assertions.assertThat(Boolean.TRUE.equals(existe)).isTrue();
    }

    private Anime createAnime() {
        Anime anime = new Anime();
        anime.setName("Spider man");
        return anime;
    }

    private Boolean existeAnimeName(List<Anime> animes) {
        for(Anime a : animes) {
            if(a.getName().equals(this.animeTest.getName())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}