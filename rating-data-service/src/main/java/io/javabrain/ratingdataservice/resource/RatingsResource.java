package io.javabrain.ratingdataservice.resource;

import io.javabrain.ratingdataservice.model.Rating;
import io.javabrain.ratingdataservice.model.UserRatingData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRatingsData(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRatingData getUserRatingData(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("123u"+userId, 123),
                new Rating("456u"+userId, 456)
        );
        UserRatingData userRatingData = new UserRatingData();

        userRatingData.setUserRating(ratings);
        return userRatingData;
    }
}
