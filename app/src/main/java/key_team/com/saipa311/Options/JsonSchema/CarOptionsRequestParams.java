package key_team.com.saipa311.Options.JsonSchema;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarOptionsRequestParams {
    @SerializedName("repId")
    @Expose
    private Integer repId;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
