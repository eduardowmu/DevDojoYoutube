package br.edu.devdojo2.app.client;

import br.edu.devdojo2.app.model.Anime;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*Classe para uso do RestTemplate*/
@Log4j2
public class SpringClient {
    public static void main(String[] args) {
       /*
       Anime anime = new RestTemplate().getForObject("http://localhost:8080/anime/1", Anime.class);
       ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/anime/{id}", Anime.class, 1);
       System.out.println(new Gson().toJson(anime));
       System.out.println(new Gson().toJson(entity));
       */

        //Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/anime", Anime[].class);

        /*
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/anime", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Anime>>() {});

        System.out.println(new Gson().toJson(exchange.getBody()));
        */

        Anime anime = new Anime();
        anime.setName("Hajime no Ipo");

        /*ResponseEntity<HttpStatus> exchange = new RestTemplate().exchange("http://localhost:8080/anime/save",
                HttpMethod.POST, new HttpEntity(anime), Anime.class);

          System.out.println(exchange.getBody());
         */

        HttpStatus status = new RestTemplate().postForObject("http://localhost:8080/anime/save",
                anime, HttpStatus.class);
        System.out.println(status);
    }
}
