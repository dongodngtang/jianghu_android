package net.doyouhike.app.bbs.biz.entity.road;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by terry on 5/12/16.
 */
public class MapBase  implements Parcelable {

    private String id;

    private String create_time;

    private String status;

    private String file_path;

    private String coord_type;

    private String create_user;


    protected MapBase(Parcel in) {
        id = in.readString();
        create_time = in.readString();
        status = in.readString();
        file_path = in.readString();
        coord_type = in.readString();
        create_user = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(create_time);
        dest.writeString(status);
        dest.writeString(file_path);
        dest.writeString(coord_type);
        dest.writeString(create_user);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MapBase> CREATOR = new Creator<MapBase>() {
        @Override
        public MapBase createFromParcel(Parcel in) {
            return new MapBase(in);
        }

        @Override
        public MapBase[] newArray(int size) {
            return new MapBase[size];
        }
    };

    public String getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(String coord_type) {
        this.coord_type = coord_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
