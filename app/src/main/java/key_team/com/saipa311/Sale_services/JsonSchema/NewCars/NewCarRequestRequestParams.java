
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarRequestRequestParams {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("ncId")
    @Expose
    private Integer ncId;
    @SerializedName("nccId")
    @Expose
    private Integer nccId;
    @SerializedName("ncrHaveLicensePlate")
    @Expose
    private Integer ncrHaveLicensePlate;
    @SerializedName("ncrDescription")
    @Expose
    private String ncrDescription;
    @SerializedName("selectedOptions")
    @Expose
    private List<SelectedOption> selectedOptions = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public Integer getNcId() {
        return ncId;
    }

    public void setNcId(Integer ncId) {
        this.ncId = ncId;
    }

    public Integer getNccId() {
        return nccId;
    }

    public void setNccId(Integer nccId) {
        this.nccId = nccId;
    }

    public Integer getNcrHaveLicensePlate() {
        return ncrHaveLicensePlate;
    }

    public void setNcrHaveLicensePlate(Integer ncrHaveLicensePlate) {
        this.ncrHaveLicensePlate = ncrHaveLicensePlate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNcrDescription() {
        return ncrDescription;
    }

    public void setNcrDescription(String ncrDescription) {
        this.ncrDescription = ncrDescription;
    }

    public List<SelectedOption> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<SelectedOption> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

}
