package br.edu.devdojo2.app.controller;

import br.edu.devdojo2.app.model.Anime;
import br.edu.devdojo2.app.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/anime")
public class AppController {
    @Autowired
    private DateUtil dateUtil;

    @GetMapping("/list")
    public ResponseEntity<List<Anime>> findAll() {
        System.out.println("Buscado em ".concat(dateUtil.formatLocalDateStyle(LocalDateTime.now())));
        List<Anime> animes = new ArrayList<>();

        animes.add(Anime.builder().name("Naruto").build());
        animes.add(Anime.builder().name("Saint Seiya").build());

        return ResponseEntity.ok(animes);
    }
}