package tk.mirenamorrortu.todolistfragments.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bafi on 23/03/15.
 */
public class ToDo implements Parcelable {
    private String task;
    private Date created;

    private ToDo todo;
    private int mData;

    public ToDo(String task) {
        this.task = task;
        this.created = new Date();
    }

    public ToDo(String task, Date created) {
        this(task);
        this.created = created;
    }

    public String getTask() {
        return task;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedFormated() {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy/MM/dd");
        return smp.format(created.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(task);
        dest.writeLong(created.getTime());
    }

    private ToDo (Parcel in){
        //hay que leerlos en el mismo orden en el que los hemos escrito!!!!!!!!!!!!
        task = in.readString();
        created = new Date(in.readLong());
    }


    public static final Parcelable.Creator<ToDo> CREATOR
            = new Parcelable.Creator<ToDo>() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };
}
