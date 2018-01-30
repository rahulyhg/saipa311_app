package key_team.com.saipa311.Options.JsonSchema;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarOptionRequestExistsParams {
    @SerializedName("repId")
    @Expose
    private Integer repId;

    @SerializedName("coId")
    @Expose
    private Integer coId;

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }
}
