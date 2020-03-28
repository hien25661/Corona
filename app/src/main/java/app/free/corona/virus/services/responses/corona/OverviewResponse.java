package app.free.corona.virus.services.responses.corona;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverviewResponse {

    @SerializedName("cases")
    @Expose
    private Long cases;
    @SerializedName("deaths")
    @Expose
    private Long deaths;
    @SerializedName("recovered")
    @Expose
    private Long recovered;
    @SerializedName("updated")
    @Expose
    private Long updated;
    @SerializedName("active")
    @Expose
    private Long active;

    public Long getCases() {
        return cases;
    }

    public void setCases(Long cases) {
        this.cases = cases;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

}