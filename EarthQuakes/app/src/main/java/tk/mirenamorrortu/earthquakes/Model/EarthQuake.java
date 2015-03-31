package tk.mirenamorrortu.earthquakes.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bafi on 25/03/15.
 */
public class EarthQuake implements Parcelable {

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

    public String getTimeFormatted(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(this.time);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeDouble(magnitude);
        dest.writeLong(time.getTime());
        dest.writeString(_id);
        dest.writeString(place);
        dest.writeString(url);
        dest.writeDouble(coords.getLat());
        dest.writeDouble(coords.getLng());
        dest.writeDouble(coords.getDepth());
    }

    private EarthQuake (Parcel in){
        //hay que leerlos en el mismo orden en el que los hemos escrito!!!!!!!!!!!!
        magnitude = in.readDouble();
        time = new Date(in.readLong());
        _id = in.readString();
        place = in.readString();
        url = in.readString();
        coords = new Coordinate(in.readDouble(), in.readDouble(), in.readDouble());
    }


    public static final Parcelable.Creator<EarthQuake> CREATOR
            = new Parcelable.Creator<EarthQuake>() {
        public EarthQuake createFromParcel(Parcel in) {
            return new EarthQuake(in);
        }

        public EarthQuake[] newArray(int size) {
            return new EarthQuake[size];
        }
    };

}
