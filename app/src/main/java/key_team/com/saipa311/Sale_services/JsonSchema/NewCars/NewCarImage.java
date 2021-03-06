
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
    @SerializedName("nciPath")
    @Expose
    private String nciPath;
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

    public String getNciPath() {
        return nciPath;
    }

    public void setNciPath(String nciPath) {
        this.nciPath = nciPath;
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
