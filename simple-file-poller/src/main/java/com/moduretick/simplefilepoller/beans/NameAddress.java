package com.moduretick.simplefilepoller.beans;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";")
public class NameAddress {
	
	@DataField(pos = 1, columnName = "name")
	private String name;
	@DataField(pos = 2, columnName = "house_number")
	private String houseNumber;
	@DataField(pos = 3, columnName = "city")
	private String city;
	@DataField(pos = 4, columnName = "province")
	private String province;
	@DataField(pos = 5, columnName = "postal_code")
	private String postalCode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	@Override
	public String toString() {
		return "NameAddress [name=" + name + ", houseNumber=" + houseNumber + ", city=" + city + ", province="
				+ province + ", postalCode=" + postalCode + "]";
	}
	
	
	
}
