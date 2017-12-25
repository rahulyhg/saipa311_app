
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarRequestRequestParams {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("nationalCode")
    @Expose
    private String nationalCode;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("ncId")
    @Expose
    private Integer ncId;
    @SerializedName("ncrHaveLicensePlate")
    @Expose
    private Integer ncrHaveLicensePlate;
    @SerializedName("ncrAddress")
    @Expose
    private String ncrAddress;
    @SerializedName("ncrDescription")
    @Expose
    private String ncrDescription;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getNcrHaveLicensePlate() {
        return ncrHaveLicensePlate;
    }

    public void setNcrHaveLicensePlate(Integer ncrHaveLicensePlate) {
        this.ncrHaveLicensePlate = ncrHaveLicensePlate;
    }

    public String getNcrAddress() {
        return ncrAddress;
    }

    public void setNcrAddress(String ncrAddress) {
        this.ncrAddress = ncrAddress;
    }

    public String getNcrDescription() {
        return ncrDescription;
    }

    public void setNcrDescription(String ncrDescription) {
        this.ncrDescription = ncrDescription;
    }

}
