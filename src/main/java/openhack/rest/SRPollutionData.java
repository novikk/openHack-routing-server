package openhack.rest;
public class SRPollutionData {
	private double latitude;
	private double longitude;
	private double value;
	
	public SRPollutionData(double lat, double lon, double val) {
		this.latitude = lat;
		this.longitude = lon;
		this.value = val;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}