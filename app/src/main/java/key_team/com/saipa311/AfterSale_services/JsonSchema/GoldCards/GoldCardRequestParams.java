package key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoldCardRequestParams {
    @SerializedName("repId")
    @Expose
    private Integer repId;

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }
}
