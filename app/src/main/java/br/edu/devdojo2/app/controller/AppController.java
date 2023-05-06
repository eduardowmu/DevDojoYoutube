package br.edu.devdojo2.app.controller;

import br.edu.devdojo2.app.Exception.NotFoundException;
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

@RestController
@RequestMapping("/anime")
public class AppController {
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private AnimeService animeService;

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveAnime(@RequestBody Anime anime) {
        return ResponseEntity.ok(this.animeService.save(anime) != null ? HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Anime>> findAll() {
        System.out.println("Buscado em ".concat(dateUtil.formatLocalDateStyle(LocalDateTime.now())));

        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        var anime = this.animeService.findById(id);
        return ResponseEntity.ok(anime.orElse(NotFoundException.notFoundException(new Anime())));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAnime(@PathVariable Long id) {
        var anime = this.animeService.findById(id);
        if(anime.isPresent()) {
            this.animeService.delete(anime.get());
            return ResponseEntity.ok(HttpStatus.resolve(200));
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateAnime(@RequestBody Anime anime) {
        var request = this.animeService.findById(anime.getId());
        return ResponseEntity.ok(request.isPresent() && this.animeService.save(anime) != null ?
                HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
    }
}