package br.edu.devdojo2.app.service;

import br.edu.devdojo2.app.Exception.BadRequestException;
import br.edu.devdojo2.app.Exception.NotFoundException;
import br.edu.devdojo2.app.dto.AnimePostReqDto;
import br.edu.devdojo2.app.dto.AnimeRespDto;
import br.edu.devdojo2.app.mapper.AnimeMapper;
import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;

    private final AnimeMapper animeMapper;

    @Autowired
    public AnimeService(AnimeRepository animeRepository, AnimeMapper animeMapper) {
        this.animeRepository = animeRepository;
        this.animeMapper = animeMapper;
    }

    /*Esta notação serve para quando queremos que, numa ação
    *indesejada na base de dados, o spring realize o rollback,
    *em ocorrência de Exception*/
    @Transactional(rollbackFor = Exception.class)
    public Anime save(AnimePostReqDto reqDto) {
        Anime anime = animeMapper.toModel(reqDto);
        return this.animeRepository.save(anime);
    }

    public Page<Anime> listAll(Pageable pageable) {
        Page<Anime> animes = this.animeRepository.findAll(pageable);
        return animes;
    }

    public List<Anime> findAll() {
        return this.animeRepository.findAll();
    }

    @Cacheable(cacheNames = "animes")
    public Anime findById(Long id) {
    //public Optional<Anime> findById(Long id) {
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
        System.out.println("anime");
        return this.animeRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Anime nao encontrado"));
    }

    public List<AnimeRespDto> findByName(String name) {
        List<Anime> animes = this.animeRepository.findByName(name);
        List<AnimeRespDto> respDtos = new ArrayList<>();
        animes.stream().forEach(a -> respDtos.add(this.animeMapper.toRespDto(a)));
        return respDtos;
    }

    public void delete(Anime anime) {
        this.animeRepository.delete(anime);
    }
}