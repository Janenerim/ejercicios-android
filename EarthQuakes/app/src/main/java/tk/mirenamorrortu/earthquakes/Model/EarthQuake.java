package tk.mirenamorrortu.earthquakes.Model;

import java.util.Date;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuake {

    private Date time;

    private Coordinate coords;

    private double magnitude;

    private String place;

    private String _id;

    private String url;

    public EarthQuake() {
    }

    public EarthQuake(Date time, Coordinate coords, double magnitude, String place, String _id, String url) {
        this.time = time;
        this.coords = coords;
        this.magnitude = magnitude;
        this.place = place;
        this._id = _id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = new Date (time);
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return this.getPlace();
    }
}
