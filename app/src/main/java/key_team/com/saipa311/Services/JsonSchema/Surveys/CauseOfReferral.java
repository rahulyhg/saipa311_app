
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CauseOfReferral {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("corState")
    @Expose
    private Integer corState;
    @SerializedName("corSubject")
    @Expose
    private String corSubject;
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

    public Integer getCorState() {
        return corState;
    }

    public void setCorState(Integer corState) {
        this.corState = corState;
    }

    public String getCorSubject() {
        return corSubject;
    }

    public void setCorSubject(String corSubject) {
        this.corSubject = corSubject;
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
