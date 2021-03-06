package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarRequestExistsParams {
    @SerializedName("repId")
    @Expose
    private Integer repId;

    @SerializedName("ncId")
    @Expose
    private Integer ncId;

    public Integer getNcId() {
        return ncId;
    }

    public void setNcId(Integer ncId) {
        this.ncId = ncId;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }
}
