/*
 * CoindeskPrice.java
 *
 * Created on 2017-07-15, 15:54
 */
package com.marcnuri.projectreactordemo.model.coindesk;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2017-07-15.
 */
public class CoindeskPrice {

//**************************************************************************************************
//  Fields
//**************************************************************************************************
private static final Logger LOGGER = Logger.getLogger(CoindeskPrice.class.getName());

	private final DecimalFormat doubleFormat;
	private Date updatedISO;
	private Double dollarPrice;
	private Double euroPrice;

//**************************************************************************************************
//  Constructors
//**************************************************************************************************
	public CoindeskPrice() {
		doubleFormat = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		doubleFormat.applyLocalizedPattern("#,##.0");
	}

//**************************************************************************************************
//  Abstract Methods
//**************************************************************************************************

//**************************************************************************************************
//  Overridden Methods
//**************************************************************************************************

//**************************************************************************************************
//  Other Methods
//**************************************************************************************************
	@JsonProperty("time")
	private void updatedISOUnmarshall(Map<String, String> time) {
		updatedISO = Date.from(OffsetDateTime.parse(time.get("updatedISO").toString()).toInstant());
	}
	@JsonProperty("bpi")
	private void priceUnmarshall(Map<String, Map<String, String>> bpi) {
		try {
			dollarPrice = doubleFormat.parse(bpi.get("USD").get("rate")).doubleValue();
			euroPrice = doubleFormat.parse(bpi.get("EUR").get("rate")).doubleValue();
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "",e);
		}
	}

//**************************************************************************************************
//  Getter/Setter Methods
//**************************************************************************************************
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	public Date getUpdatedISO() {
		return updatedISO;
	}

	public void setUpdatedISO(Date updatedISO) {
		this.updatedISO = updatedISO;
	}

	public Double getDollarPrice() {
		return dollarPrice;
	}

	public void setDollarPrice(Double dollarPrice) {
		this.dollarPrice = dollarPrice;
	}

	public Double getEuroPrice() {
		return euroPrice;
	}

	public void setEuroPrice(Double euroPrice) {
		this.euroPrice = euroPrice;
	}


//**************************************************************************************************
//  Static Methods
//**************************************************************************************************

//**************************************************************************************************
//  Inner Classes
//**************************************************************************************************

}
