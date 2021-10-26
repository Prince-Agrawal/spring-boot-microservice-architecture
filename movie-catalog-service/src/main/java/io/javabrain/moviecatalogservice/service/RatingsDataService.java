package io.javabrain.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrain.moviecatalogservice.model.Rating;
import io.javabrain.moviecatalogservice.model.UserRatingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RatingsDataService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackRatingsData",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public UserRatingData getRatingsData(String userId){
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId , UserRatingData.class);
    }

    public UserRatingData getFallbackRatingsData(String userId){
        return new UserRatingData(Arrays.asList(new Rating("0" , 0)));
    }
}
