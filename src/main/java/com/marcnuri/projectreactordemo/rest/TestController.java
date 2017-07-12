package com.marcnuri.projectreactordemo.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.beans.ConstructorProperties;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@RestController
@RequestMapping(path="/test")
public class TestController {

    private static final Logger LOGGER = Logger.getLogger(TestController.class.getName());

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

    @RequestMapping(value = "/flux", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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

    @GetMapping("helloDelay/{who}")
    public Mono<String> helloDelay(@PathVariable String who) {
        return Mono.just("Hello " + who + "!!")
                .delaySubscription(Duration.ofSeconds(5));
    }

    @GetMapping("heyMister")
    public Flux<String> hey(@RequestParam("mr") String mr) {
        return Mono.just("Hey mister ")
                .concatWith(Mono.just(mr)
                ).concatWith(Mono.just(". how are you?"));
    }

    @CrossOrigin
    @GetMapping("/events/{id}")
    public Mono<Event> eventById(@PathVariable long id) {
        return Mono.just(new Event(id, LocalDate.now()));
    }

    @CrossOrigin
    @GetMapping(value = "events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> events() {
        //https://github.com/bclozel/spring-reactive-university/blob/master/pom.xml
        LOGGER.info("Getting events");
        Flux<Event> eventFlux = Flux.fromStream(
                Stream.generate(
                        ()->new Event(System.currentTimeMillis(), LocalDate.now()))
        );

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
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