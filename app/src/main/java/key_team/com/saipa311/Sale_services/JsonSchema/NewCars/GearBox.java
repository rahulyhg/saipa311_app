
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GearBox {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gbGearBox")
    @Expose
    private String gbGearBox;
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

    public String getGbGearBox() {
        return gbGearBox;
    }

    public void setGbGearBox(String gbGearBox) {
        this.gbGearBox = gbGearBox;
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
