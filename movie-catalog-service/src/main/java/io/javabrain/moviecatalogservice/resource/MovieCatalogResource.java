package io.javabrain.moviecatalogservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrain.moviecatalogservice.model.CatalogItem;
import io.javabrain.moviecatalogservice.model.Movie;
import io.javabrain.moviecatalogservice.model.Rating;
import io.javabrain.moviecatalogservice.model.UserRatingData;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    List<Rating> ratings = Arrays.asList(
            new Rating("123", 123),
            new Rating("456", 456)
    );


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRatingData userRatingData = getRatingsData(userId);
        return
        userRatingData.getUserRating().stream().map(rating -> {
            Movie movie = getMovieData(rating);
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getFallbackRatingsData")
    private UserRatingData getRatingsData(String userId){
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId , UserRatingData.class);
    }

    private UserRatingData getFallbackRatingsData(String userId){
        return new UserRatingData(Arrays.asList(new Rating("0" , 0)));
    }

    @HystrixCommand(fallbackMethod = "getFallbackMoviesData")
    private Movie getMovieData(Rating rating){
        return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    }

    private Movie getFallbackMoviesData(Rating rating){
        return new Movie("0" , "No movie" , "No desc");
    }

    // fallback method
    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatalogItem("no movie", "no", 0));
    }

//web client

//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();
}
