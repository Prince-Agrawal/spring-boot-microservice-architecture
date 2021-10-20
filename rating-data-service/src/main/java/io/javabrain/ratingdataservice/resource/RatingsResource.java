package io.javabrain.ratingdataservice.resource;

import io.javabrain.ratingdataservice.model.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRatingsData(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }
}
