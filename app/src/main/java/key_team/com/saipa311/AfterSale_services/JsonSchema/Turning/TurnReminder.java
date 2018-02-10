
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TurnReminder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("acId")
    @Expose
    private Integer acId;
    @SerializedName("dId")
    @Expose
    private Integer dId;
    @SerializedName("mcId")
    @Expose
    private Integer mcId;
    @SerializedName("trReminderDelivery")
    @Expose
    private Integer trReminderDelivery;
    @SerializedName("trState")
    @Expose
    private Integer trState;
    @SerializedName("trResultDescription")
    @Expose
    private Object trResultDescription;
    @SerializedName("trTrackingCode")
    @Expose
    private String trTrackingCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("admission_capacity")
    @Expose
    private AdmissionCapacity admissionCapacity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public Integer getMcId() {
        return mcId;
    }

    public void setMcId(Integer mcId) {
        this.mcId = mcId;
    }

    public Integer getTrReminderDelivery() {
        return trReminderDelivery;
    }

    public void setTrReminderDelivery(Integer trReminderDelivery) {
        this.trReminderDelivery = trReminderDelivery;
    }

    public Integer getTrState() {
        return trState;
    }

    public void setTrState(Integer trState) {
        this.trState = trState;
    }

    public Object getTrResultDescription() {
        return trResultDescription;
    }

    public void setTrResultDescription(Object trResultDescription) {
        this.trResultDescription = trResultDescription;
    }

    public String getTrTrackingCode() {
        return trTrackingCode;
    }

    public void setTrTrackingCode(String trTrackingCode) {
        this.trTrackingCode = trTrackingCode;
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

    public AdmissionCapacity getAdmissionCapacity() {
        return admissionCapacity;
    }

    public void setAdmissionCapacity(AdmissionCapacity admissionCapacity) {
        this.admissionCapacity = admissionCapacity;
    }

}
