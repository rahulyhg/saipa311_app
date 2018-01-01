package key_team.com.saipa311.Sale_services.JsonSchema.OldCars;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OldCarRequestExistsParams {
    @SerializedName("ocId")
    @Expose
    private Integer ocId;

    public Integer getOcId() {
        return ocId;
    }

    public void setOcId(Integer ocId) {
        this.ocId = ocId;
    }
}
