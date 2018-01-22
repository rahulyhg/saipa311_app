
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeclarationGroupRequestParams {

    @SerializedName("astId")
    @Expose
    private Integer astId;

    public Integer getAstId() {
        return astId;
    }

    public void setAstId(Integer astId) {
        this.astId = astId;
    }

}
