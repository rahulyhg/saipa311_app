
package key_team.com.saipa311.Customer_services.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackingCode {

    @SerializedName("trackingCode")
    @Expose
    private String trackingCode;

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

}
