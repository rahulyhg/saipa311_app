
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chassis {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("chChassis")
    @Expose
    private String chChassis;
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

    public String getChChassis() {
        return chChassis;
    }

    public void setChChassis(String chChassis) {
        this.chChassis = chChassis;
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
