package io.javabrain.moviecatalogservice.model;

import java.util.List;

public class UserRatingData {
    private List<Rating> userRating;

    public UserRatingData() {
    }
    public UserRatingData(List<Rating> userRating) {
        this.userRating = userRating;
    }

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
