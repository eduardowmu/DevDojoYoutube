package br.edu.devdojo2.app.controller;

import br.edu.devdojo2.app.Exception.BadRequestException;
import br.edu.devdojo2.app.Exception.NotFoundException;
import br.edu.devdojo2.app.dto.AnimePostReqDto;
import br.edu.devdojo2.app.dto.AnimeRespDto;
import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.service.AnimeService;
import br.edu.devdojo2.app.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anime")
public class AppController {
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private AnimeService animeService;

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveAnime(@RequestBody AnimePostReqDto reqDto) {
        return ResponseEntity.ok(this.animeService.save(reqDto) != null ?
                HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AnimeRespDto>> findAll() {
        System.out.println("Buscado em ".concat(dateUtil.formatLocalDateStyle(LocalDateTime.now())));
        List<AnimeRespDto> respDtos = animeService.listAll();;
        return ResponseEntity.ok(respDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        Optional<Anime> anime = this.animeService.findById(id);
        return ResponseEntity.ok(anime.orElse(NotFoundException.notFoundException(new Anime())));
    }

    /*A diferença entre PathVariable e RequestParam é que usando PathVariable,
    *Por mais que vc insira um nome de parametro diferente, o Spring não consegue
    *diferenciar o Long id do metodo findById() de findByName(). Além disso, o
    *PathVariable não consegue reconhecer valor com espaço(s).*/
    @GetMapping("/find")
    public ResponseEntity<List<AnimeRespDto>> findByName(@RequestParam String name) {
        List<AnimeRespDto> respDtos = this.animeService.findByName(name);
        return ResponseEntity.ok(respDtos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAnime(@PathVariable Long id) {
        Optional<Anime> anime = this.animeService.findById(id);
        if(anime.isPresent()) {
            this.animeService.delete(anime.get());
            return ResponseEntity.ok(HttpStatus.resolve(200));
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateAnime(@RequestBody AnimePostReqDto reqDto) {
        Optional<Anime> request = this.animeService.findById(reqDto.getId());
        return ResponseEntity.ok(request.isPresent() && this.animeService.save(reqDto) != null ?
                HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
    }
}