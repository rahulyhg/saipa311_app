
package key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutDatedCarChangePlans {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ccpState")
    @Expose
    private Integer ccpState;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("ccpSubject")
    @Expose
    private String ccpSubject;
    @SerializedName("ccpStartYear")
    @Expose
    private String ccpStartYear;
    @SerializedName("ccpEndYear")
    @Expose
    private String ccpEndYear;
    @SerializedName("ccpDescription")
    @Expose
    private String ccpDescription;
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

    public Integer getCcpState() {
        return ccpState;
    }

    public void setCcpState(Integer ccpState) {
        this.ccpState = ccpState;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public String getCcpSubject() {
        return ccpSubject;
    }

    public void setCcpSubject(String ccpSubject) {
        this.ccpSubject = ccpSubject;
    }

    public String getCcpStartYear() {
        return ccpStartYear;
    }

    public void setCcpStartYear(String ccpStartYear) {
        this.ccpStartYear = ccpStartYear;
    }

    public String getCcpEndYear() {
        return ccpEndYear;
    }

    public void setCcpEndYear(String ccpEndYear) {
        this.ccpEndYear = ccpEndYear;
    }

    public String getCcpDescription() {
        return ccpDescription;
    }

    public void setCcpDescription(String ccpDescription) {
        this.ccpDescription = ccpDescription;
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
