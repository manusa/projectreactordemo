/*
 * CurrencyController.java
 *
 * Created on 2017-07-15, 16:04
 */
package com.marcnuri.projectreactordemo.rest;

import com.marcnuri.projectreactordemo.model.coindesk.CoindeskPrice;
import com.marcnuri.projectreactordemo.service.CoindeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-07-15.
 */
@RestController
@RequestMapping(path="/api/currency")
public class CurrencyController {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
	private static final Logger LOGGER = Logger.getLogger(CurrencyController.class.getName());

	@Autowired
	private CoindeskService coindeskService;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************
	@CrossOrigin
	@GetMapping(value = "bitcoin", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CoindeskPrice> getBitcoinPrice() {
		//https://github.com/bclozel/spring-reactive-university/blob/master/pom.xml
		LOGGER.info("Getting prices");
		Flux<CoindeskPrice> priceFlux = Flux.fromStream(
				Stream.generate(()->coindeskService.getCurrentPrice())
		);

		Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));

		return Flux.zip(priceFlux, durationFlux).map(Tuple2::getT1);
	}

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************

//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}
