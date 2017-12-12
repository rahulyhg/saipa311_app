
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarImage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nciState")
    @Expose
    private Integer nciState;
    @SerializedName("nciNcId")
    @Expose
    private Integer nciNcId;
    @SerializedName("nciPatch")
    @Expose
    private String nciPatch;
    @SerializedName("nciName")
    @Expose
    private String nciName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNciState() {
        return nciState;
    }

    public void setNciState(Integer nciState) {
        this.nciState = nciState;
    }

    public Integer getNciNcId() {
        return nciNcId;
    }

    public void setNciNcId(Integer nciNcId) {
        this.nciNcId = nciNcId;
    }

    public String getNciPatch() {
        return nciPatch;
    }

    public void setNciPatch(String nciPatch) {
        this.nciPatch = nciPatch;
    }

    public String getNciName() {
        return nciName;
    }

    public void setNciName(String nciName) {
        this.nciName = nciName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
