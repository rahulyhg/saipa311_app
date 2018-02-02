
package key_team.com.saipa311.Services.JsonSchema.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventImg {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eId")
    @Expose
    private Integer eId;
    @SerializedName("eiState")
    @Expose
    private Integer eiState;
    @SerializedName("eiPath")
    @Expose
    private String eiPath;
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

    public Integer getEId() {
        return eId;
    }

    public void setEId(Integer eId) {
        this.eId = eId;
    }

    public Integer getEiState() {
        return eiState;
    }

    public void setEiState(Integer eiState) {
        this.eiState = eiState;
    }

    public String getEiPath() {
        return eiPath;
    }

    public void setEiPath(String eiPath) {
        this.eiPath = eiPath;
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
