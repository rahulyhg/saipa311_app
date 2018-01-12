
package key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoldCard {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gcState")
    @Expose
    private Integer gcState;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("gcSubject")
    @Expose
    private String gcSubject;
    @SerializedName("gcChassisIdNumber")
    @Expose
    private String gcChassisIdNumber;
    @SerializedName("gcImgPath")
    @Expose
    private String gcImgPath;
    @SerializedName("gcPrice")
    @Expose
    private String gcPrice;
    @SerializedName("gcDescription")
    @Expose
    private String gcDescription;
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

    public Integer getGcState() {
        return gcState;
    }

    public void setGcState(Integer gcState) {
        this.gcState = gcState;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public String getGcSubject() {
        return gcSubject;
    }

    public void setGcSubject(String gcSubject) {
        this.gcSubject = gcSubject;
    }

    public String getGcChassisIdNumber() {
        return gcChassisIdNumber;
    }

    public void setGcChassisIdNumber(String gcChassisIdNumber) {
        this.gcChassisIdNumber = gcChassisIdNumber;
    }

    public String getGcImgPath() {
        return gcImgPath;
    }

    public void setGcImgPath(String gcImgPath) {
        this.gcImgPath = gcImgPath;
    }

    public String getGcPrice() {
        return gcPrice;
    }

    public void setGcPrice(String gcPrice) {
        this.gcPrice = gcPrice;
    }

    public String getGcDescription() {
        return gcDescription;
    }

    public void setGcDescription(String gcDescription) {
        this.gcDescription = gcDescription;
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
