package lab10;

import java.sql.ResultSet;
import java.sql.SQLException;

public class cityRecord implements Comparable<cityRecord> {
	
	String country = "",city = "",region = "";
	int id = 0, postalCode = 0,latitude = 0,longitude = 0,metroCode = 0,areaCode = 0;
	double distance = 0;
	
	cityRecord(ResultSet rs, double dist) throws SQLException{
		this.id = rs.getInt("id");
		this.country = rs.getString("country");
		this.city = rs.getString("city");
		this.region = rs.getString("region");
		this.postalCode = rs.getInt("postalCode");
		this.latitude = rs.getInt("latitude");
		this.longitude = rs.getInt("longitude");
		this.metroCode = rs.getInt("metroCode");
		this.areaCode = rs.getInt("areaCode");
		this.distance = dist;
	}
	@Override
	public String toString() {
		return "Id: " + id +  " Country: " + country + " City: " + city + " Region: " + region + " Distance: " + distance;
	}
	@Override
	public int compareTo(cityRecord r) {
		double compareage=((cityRecord)r).getDistance();
        /* For Ascending order*/
        return Double.compare(this.distance, compareage);
	}
	
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getMetroCode() {
		return metroCode;
	}

	public void setMetroCode(int metroCode) {
		this.metroCode = metroCode;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	

}
