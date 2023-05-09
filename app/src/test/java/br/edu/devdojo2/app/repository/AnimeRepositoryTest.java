package br.edu.devdojo2.app.repository;

import br.edu.devdojo2.app.model.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

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
    }

    @Test
    @DisplayName("Save Anime when successfull")
    void saveAnimeSucessfull() {
        animeSaved = this.animeRepository.save(animeTest);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName().equals(animeTest.getName())).isTrue();
    }

    @Test
    @DisplayName("Update Anime when successfull")
    void updatedAnimeSucessfull() {
        String name = this.animeTest.getName();
        animeSaved = this.animeRepository.save(animeTest);

        animeSaved.setName("Huck");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeSaved.getId().equals(animeUpdated.getId())).isTrue();
        Assertions.assertThat(animeUpdated.getName().equals(name)).isFalse();
    }

    @Test
    @DisplayName("Deleted Anime when successfull")
    void deletedAnimeSucessfull() {
        animeSaved = this.animeRepository.save(animeTest);

        Long id = animeSaved.getId();

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeDeleted = this.animeRepository.findById(id);

        Assertions.assertThat(animeDeleted.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find Anime by name when successfull")
    void findByNameSucessfull() {
        animeSaved = this.animeRepository.save(animeTest);

        List<Anime> animes = this.animeRepository.findByName(this.animeTest.getName());

        Boolean existe = this.existeAnimeName(animes);

        Assertions.assertThat(Boolean.TRUE.equals(existe)).isTrue();
    }

    @Test
    @DisplayName("Save Anime throw constraint validation exception")
    void saveAnimeThrowValidatedException() {
        this.animeTest.setName(null);

        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeTest))
                .isInstanceOf(DataIntegrityViolationException.class);

        /*
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(this.animeTest))
                .withMessageContaining("The name must not be null,The name must not be empty");
        */
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