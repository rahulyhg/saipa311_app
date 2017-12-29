
package key_team.com.saipa311.Sale_services.JsonSchema.OldCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OldCarRequestRequestParams {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
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
    @SerializedName("ocId")
    @Expose
    private Integer ocId;
    @SerializedName("ocrAddress")
    @Expose
    private String ocrAddress;
    @SerializedName("ocrDescription")
    @Expose
    private String ocrDescription;

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

    public Integer getOcId() {
        return ocId;
    }

    public void setOcId(Integer ocId) {
        this.ocId = ocId;
    }

    public String getOcrAddress() {
        return ocrAddress;
    }

    public void setOcrAddress(String ocrAddress) {
        this.ocrAddress = ocrAddress;
    }

    public String getOcrDescription() {
        return ocrDescription;
    }

    public void setOcrDescription(String ocrDescription) {
        this.ocrDescription = ocrDescription;
    }

}
