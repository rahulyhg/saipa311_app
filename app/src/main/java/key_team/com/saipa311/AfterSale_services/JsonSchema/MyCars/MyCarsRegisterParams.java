
package key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCarsRegisterParams {

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
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pId")
    @Expose
    private Integer pId;
    @SerializedName("chassisIdNumber")
    @Expose
    private String chassisIdNumber;
    @SerializedName("guaranteeStartDate")
    @Expose
    private String guaranteeStartDate;
    @SerializedName("buildYear")
    @Expose
    private String buildYear;
    @SerializedName("licensePlate_part1")
    @Expose
    private String licensePlatePart1;
    @SerializedName("licensePlate_part2")
    @Expose
    private String licensePlatePart2;
    @SerializedName("licensePlate_part3")
    @Expose
    private String licensePlatePart3;
    @SerializedName("licensePlate_part4")
    @Expose
    private String licensePlatePart4;
    @SerializedName("licensePlate_part5")
    @Expose
    private String licensePlatePart5;
    @SerializedName("engineIdNumber")
    @Expose
    private String engineIdNumber;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getChassisIdNumber() {
        return chassisIdNumber;
    }

    public void setChassisIdNumber(String chassisIdNumber) {
        this.chassisIdNumber = chassisIdNumber;
    }

    public String getGuaranteeStartDate() {
        return guaranteeStartDate;
    }

    public void setGuaranteeStartDate(String guaranteeStartDate) {
        this.guaranteeStartDate = guaranteeStartDate;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getLicensePlatePart1() {
        return licensePlatePart1;
    }

    public void setLicensePlatePart1(String licensePlatePart1) {
        this.licensePlatePart1 = licensePlatePart1;
    }

    public String getLicensePlatePart2() {
        return licensePlatePart2;
    }

    public void setLicensePlatePart2(String licensePlatePart2) {
        this.licensePlatePart2 = licensePlatePart2;
    }

    public String getLicensePlatePart3() {
        return licensePlatePart3;
    }

    public void setLicensePlatePart3(String licensePlatePart3) {
        this.licensePlatePart3 = licensePlatePart3;
    }

    public String getLicensePlatePart4() {
        return licensePlatePart4;
    }

    public void setLicensePlatePart4(String licensePlatePart4) {
        this.licensePlatePart4 = licensePlatePart4;
    }

    public String getLicensePlatePart5() {
        return licensePlatePart5;
    }

    public void setLicensePlatePart5(String licensePlatePart5) {
        this.licensePlatePart5 = licensePlatePart5;
    }

    public String getEngineIdNumber() {
        return engineIdNumber;
    }

    public void setEngineIdNumber(String engineIdNumber) {
        this.engineIdNumber = engineIdNumber;
    }

}
