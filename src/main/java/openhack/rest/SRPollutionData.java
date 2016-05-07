package openhack.rest;
public class SRPollutionData {
	private float latitude;
	private float longitude;
	private float value;
	
	public SRPollutionData(float lat, float lon, float val) {
		this.latitude = lat;
		this.longitude = lon;
		this.value = val;
	}
	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
}