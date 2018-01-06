
package key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutDatedCarChangePlanRequestParams {

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
