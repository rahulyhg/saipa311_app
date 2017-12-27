
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarOption {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coOId")
    @Expose
    private Integer coOId;
    @SerializedName("coPId")
    @Expose
    private Integer coPId;
    @SerializedName("coReId")
    @Expose
    private Integer coReId;
    @SerializedName("coPrice")
    @Expose
    private String coPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("option")
    @Expose
    private Option option;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoOId() {
        return coOId;
    }

    public void setCoOId(Integer coOId) {
        this.coOId = coOId;
    }

    public Integer getCoPId() {
        return coPId;
    }

    public void setCoPId(Integer coPId) {
        this.coPId = coPId;
    }

    public Integer getCoReId() {
        return coReId;
    }

    public void setCoReId(Integer coReId) {
        this.coReId = coReId;
    }

    public String getCoPrice() {
        return coPrice;
    }

    public void setCoPrice(String coPrice) {
        this.coPrice = coPrice;
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

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

}
