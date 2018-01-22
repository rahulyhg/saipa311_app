
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeclarationRequestParams {

    @SerializedName("dgId")
    @Expose
    private Integer dgId;

    public Integer getDgId() {
        return dgId;
    }

    public void setDgId(Integer dgId) {
        this.dgId = dgId;
    }

}
