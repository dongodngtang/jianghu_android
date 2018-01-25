package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * Created by zaitu on 15-11-26.
 */
public class Location {

    /**
     * latitude : 0.000000000
     * longitude : 0.000000000
     * altitude : 0.0
     * city_id : 0
     * city :
     * location :
     */

    private String latitude;
    private String longitude;
    private String altitude;
    private String city_id;
    private String city;
    private String location;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
