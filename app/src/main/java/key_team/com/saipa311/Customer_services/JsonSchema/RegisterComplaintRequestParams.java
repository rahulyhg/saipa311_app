
package key_team.com.saipa311.Customer_services.JsonSchema;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterComplaintRequestParams {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("nationalCode")
    @Expose
    private String nationalCode;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("repId")
    @Expose
    private Integer repId;
    @SerializedName("mcId")
    @Expose
    private Integer mcId;
    @SerializedName("receptionNumber")
    @Expose
    private String receptionNumber;
    @SerializedName("receptionDate")
    @Expose
    private String receptionDate;
    @SerializedName("kmOfOperation")
    @Expose
    private String kmOfOperation;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("selectedComplaints")
    @Expose
    private List<SelectedComplaint> selectedComplaints = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public Integer getMcId() {
        return mcId;
    }

    public void setMcId(Integer mcId) {
        this.mcId = mcId;
    }

    public String getReceptionNumber() {
        return receptionNumber;
    }

    public void setReceptionNumber(String receptionNumber) {
        this.receptionNumber = receptionNumber;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    public String getKmOfOperation() {
        return kmOfOperation;
    }

    public void setKmOfOperation(String kmOfOperation) {
        this.kmOfOperation = kmOfOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectedComplaint> getSelectedComplaints() {
        return selectedComplaints;
    }

    public void setSelectedComplaints(List<SelectedComplaint> selectedComplaints) {
        this.selectedComplaints = selectedComplaints;
    }

}
