
package key_team.com.saipa311.Customer_services.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Complaint {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ctId")
    @Expose
    private Integer ctId;
    @SerializedName("cSubject")
    @Expose
    private String cSubject;
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

    public Integer getCtId() {
        return ctId;
    }

    public void setCtId(Integer ctId) {
        this.ctId = ctId;
    }

    public String getCSubject() {
        return cSubject;
    }

    public void setCSubject(String cSubject) {
        this.cSubject = cSubject;
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
