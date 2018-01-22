
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Declaration {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dgId")
    @Expose
    private Integer dgId;
    @SerializedName("dSubject")
    @Expose
    private String dSubject;
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

    public Integer getDgId() {
        return dgId;
    }

    public void setDgId(Integer dgId) {
        this.dgId = dgId;
    }

    public String getDSubject() {
        return dSubject;
    }

    public void setDSubject(String dSubject) {
        this.dSubject = dSubject;
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
