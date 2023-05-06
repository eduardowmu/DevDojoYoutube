package br.edu.devdojo2.app.service;

import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;

    public Anime save(Anime anime) {
        return this.animeRepository.save(anime);
    }

    public List<Anime> listAll() {
        List<Anime> animes = this.animeRepository.findAll();

        return animes;
    }

    public Optional<Anime> findById(Long id) {
        /*
        *Podemos resolver tambem assim
        *
        *return this.animeRepository.findAll()
        *   .stream().filter(a -> a.getId().equals(id))
        *   .findFirst().orElseThrow(() -> new ResponseStatusException(
        *       HttpStatus.BAD_REQUEST, "Anime nao encontrado"))
        *
        * Ou tambem podemos incluir o seguinte em application.yml
        * server:
        *   error:
        *       include-stackTrace: never
        * */
        return this.animeRepository.findById(id);
    }

    public void delete(Anime anime) {
        this.animeRepository.delete(anime);
    }
}