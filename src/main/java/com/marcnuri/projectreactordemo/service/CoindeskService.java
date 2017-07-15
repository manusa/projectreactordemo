/*
 * CoindeskService.java
 *
 * Created on 2017-07-15, 15:59
 */
package com.marcnuri.projectreactordemo.service;

import com.marcnuri.projectreactordemo.model.coindesk.CoindeskPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-07-15.
 */
@Service
public class CoindeskService {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
	private static final String API_URL = "http://api.coindesk.com/v1/bpi/currentprice.json";

	@Autowired
	private RestTemplate globalRestTemplate;

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
	public CoindeskPrice getCurrentPrice(){
		return globalRestTemplate.exchange(API_URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<CoindeskPrice>() {
		}).getBody();
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
