package key_team.com.saipa311.Sale_services.JsonSchema.Deposits;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepositRequestExistsParams {
    @SerializedName("repId")
    @Expose
    private Integer repId;

    @SerializedName("dId")
    @Expose
    private Integer dId;

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }
}
