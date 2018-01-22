
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeclarationGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("astId")
    @Expose
    private Integer astId;
    @SerializedName("dgSubject")
    @Expose
    private String dgSubject;
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

    public Integer getAstId() {
        return astId;
    }

    public void setAstId(Integer astId) {
        this.astId = astId;
    }

    public String getDgSubject() {
        return dgSubject;
    }

    public void setDgSubject(String dgSubject) {
        this.dgSubject = dgSubject;
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
