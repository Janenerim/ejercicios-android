package tk.mirenamorrortu.earthquakes.Model;

/**
 * Created by cursomovil on 25/03/15.
 */
public class Coordinate {
    /*
    Latitude
     */
    private Double lat;
    /*
    Longitude
     */
    private Double lng;
    /*
    Depth
     */
    private Double depth;

    public Coordinate(Double lat, Double lng, Double depth) {
        this.lat = lat;
        this.lng = lng;
        this.depth = depth;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getDepth() {
        return depth;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }
}
