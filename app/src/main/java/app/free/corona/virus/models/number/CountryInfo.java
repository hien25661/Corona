package app.free.corona.virus.models.number;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryInfo {

    @SerializedName("_id")
    @Expose
    private Integer id;
    @Expose
    private Long _long;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("iso3")
    @Expose
    private String iso3;
    @SerializedName("iso2")
    @Expose
    private String iso2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

}
