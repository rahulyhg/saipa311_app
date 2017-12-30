
package key_team.com.saipa311.Sale_services.JsonSchema.Deposits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepositRequestRequestParams {

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
    @SerializedName("dId")
    @Expose
    private Integer dId;
    @SerializedName("drAddress")
    @Expose
    private String drAddress;
    @SerializedName("drDescription")
    @Expose
    private String drDescription;

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

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public String getDrAddress() {
        return drAddress;
    }

    public void setDrAddress(String drAddress) {
        this.drAddress = drAddress;
    }

    public String getDrDescription() {
        return drDescription;
    }

    public void setDrDescription(String drDescription) {
        this.drDescription = drDescription;
    }

}
