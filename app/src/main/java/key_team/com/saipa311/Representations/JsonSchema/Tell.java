
package key_team.com.saipa311.Representations.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tell {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tNumber")
    @Expose
    private String tNumber;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("tState")
    @Expose
    private Integer tState;
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

    public String getTNumber() {
        return tNumber;
    }

    public void setTNumber(String tNumber) {
        this.tNumber = tNumber;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getTState() {
        return tState;
    }

    public void setTState(Integer tState) {
        this.tState = tState;
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
