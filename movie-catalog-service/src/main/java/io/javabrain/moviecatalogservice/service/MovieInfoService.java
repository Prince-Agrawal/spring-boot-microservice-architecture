package io.javabrain.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrain.moviecatalogservice.model.Movie;
import io.javabrain.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackMoviesData")
    public Movie getMovieData(Rating rating){
        return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    }

    public Movie getFallbackMoviesData(Rating rating){
        return new Movie("0" , "No movie" , "No desc");
    }
}
