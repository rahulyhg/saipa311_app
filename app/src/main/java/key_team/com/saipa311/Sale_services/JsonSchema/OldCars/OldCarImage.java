
package key_team.com.saipa311.Sale_services.JsonSchema.OldCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OldCarImage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ociState")
    @Expose
    private Integer ociState;
    @SerializedName("ociOcId")
    @Expose
    private Integer ociOcId;
    @SerializedName("ociPath")
    @Expose
    private String ociPath;
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

    public Integer getOciState() {
        return ociState;
    }

    public void setOciState(Integer ociState) {
        this.ociState = ociState;
    }

    public Integer getOciOcId() {
        return ociOcId;
    }

    public void setOciOcId(Integer ociOcId) {
        this.ociOcId = ociOcId;
    }

    public String getOciPath() {
        return ociPath;
    }

    public void setOciPath(String ociPath) {
        this.ociPath = ociPath;
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
