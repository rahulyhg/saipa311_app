
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdmissionCapacity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pId")
    @Expose
    private Integer pId;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("actId")
    @Expose
    private Integer actId;
    @SerializedName("acDate")
    @Expose
    private String acDate;
    @SerializedName("acCapacity")
    @Expose
    private Integer acCapacity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("acReservedCapacity")
    @Expose
    private Integer acReservedCapacity;
    @SerializedName("admission_capacity_time")
    @Expose
    private AdmissionCapacityTime admissionCapacityTime;
    @SerializedName("representation")
    @Expose
    private Representation representation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getAcDate() {
        return acDate;
    }

    public void setAcDate(String acDate) {
        this.acDate = acDate;
    }

    public Integer getAcCapacity() {
        return acCapacity;
    }

    public void setAcCapacity(Integer acCapacity) {
        this.acCapacity = acCapacity;
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

    public Integer getAcReservedCapacity() {
        return acReservedCapacity;
    }

    public void setAcReservedCapacity(Integer acReservedCapacity) {
        this.acReservedCapacity = acReservedCapacity;
    }

    public AdmissionCapacityTime getAdmissionCapacityTime() {
        return admissionCapacityTime;
    }

    public void setAdmissionCapacityTime(AdmissionCapacityTime admissionCapacityTime) {
        this.admissionCapacityTime = admissionCapacityTime;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

}
