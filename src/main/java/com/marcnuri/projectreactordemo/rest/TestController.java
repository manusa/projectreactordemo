package com.marcnuri.projectreactordemo.rest;

import java.beans.ConstructorProperties;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/test")
public class TestController {

  private static List<String> words = Arrays.asList(
        "the",
        "quick",
        "brown",
        "fox",
        "jumped",
        "over",
        "the",
        "lazy",
        "dog"
        );

    @RequestMapping(value = "/flux", method = RequestMethod.GET)
    public Flux getFlux(){
        return Flux.fromIterable(words);
        // return Flux.interval(Duration.ofSeconds(5))
        //         .zipWith(Flux.empty(), (no, latLng) -> {
        //             return new Address("Rome");
        //         });
    }
    
   @CrossOrigin
   @GetMapping("hello/{who}")
   public Mono<String> hello(@PathVariable String who) {
      return Mono.just(who)
                 .map(w -> "Hello " + w + "!");
   }

    @CrossOrigin
    @GetMapping("/events/{id}")
    public Mono<Event> eventById(@PathVariable long id) {
        return Mono.just(new Event(id, LocalDate.now()));
    }
    public static final class Address {

        public String getCity() {
            return city;
        }

        private final String city;

        public Address(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    '}';
        }
    }
    public static final class LatLng {

        @Min(-90)
        @Max(90)
        private final Double lat;
        private final Double lng;

        @ConstructorProperties({"lat", "lng"})
        public LatLng(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public Double getLng() {
            return lng;
        }

        @Override
        public String toString() {
            return "LatLng{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }
    public static final class Event {
        private final long id;
        private final LocalDate when;
        public Event(long id, LocalDate when){
            this.id = id;
            this.when = when;
        }
        public long getId(){
            return id;
        }

        public LocalDate getWhen(){
            return when;
        }

    }
}