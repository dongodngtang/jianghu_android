package net.doyouhike.app.bbs.biz.newnetwork.model.response.road;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zengjiang on 16/6/30.
 * 营点\路点
 */
public class FlagPoint implements Parcelable {

    public final static String FLAG_TYPE_ROAD="路点";
    public final static String FLAG_TYPE_CAMP="营地";


    /**
     * id : 10
     * flag_type : 路点 | 营点
     * flag_txt : 迦叶殿
     * offset_lngi : 100.363
     * offset_lati : 25.9679
     * coord_type : gcj02
     */

    private int id;
    private String flag_type;
    private String flag_txt;
    private double offset_lngi;
    private double offset_lati;
    private String coord_type;


    public FlagPoint() {
    }

    public FlagPoint(int id, String flag_type, String flag_txt, double offset_lngi, double offset_lati, String coord_type) {
        this.id = id;
        this.flag_type = flag_type;
        this.flag_txt = flag_txt;
        this.offset_lngi = offset_lngi;
        this.offset_lati = offset_lati;
        this.coord_type = coord_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
    }

    public String getFlag_txt() {
        return flag_txt;
    }

    public void setFlag_txt(String flag_txt) {
        this.flag_txt = flag_txt;
    }

    public double getOffset_lngi() {
        return offset_lngi;
    }

    public void setOffset_lngi(double offset_lngi) {
        this.offset_lngi = offset_lngi;
    }

    public double getOffset_lati() {
        return offset_lati;
    }

    public void setOffset_lati(double offset_lati) {
        this.offset_lati = offset_lati;
    }

    public String getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(String coord_type) {
        this.coord_type = coord_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(flag_type);
        dest.writeString(flag_txt);
        dest.writeDouble(offset_lngi);
        dest.writeDouble(offset_lati);
        dest.writeString(coord_type);

    }

    public static final Parcelable.Creator<FlagPoint> CREATOR = new Creator<FlagPoint>() {

        @Override
        public FlagPoint createFromParcel(Parcel source) {
            return new FlagPoint(source.readInt()
                    , source.readString()
                    , source.readString()
                    , source.readDouble()
                    , source.readDouble()
                    , source.readString());
        }

        @Override
        public FlagPoint[] newArray(int size) {
            return new FlagPoint[size];
        }
    };

}
