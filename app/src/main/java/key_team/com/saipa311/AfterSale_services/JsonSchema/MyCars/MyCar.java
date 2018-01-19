
package key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCar {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pId")
    @Expose
    private Integer pId;
    @SerializedName("uId")
    @Expose
    private Integer uId;
    @SerializedName("mcChassisIdNumber")
    @Expose
    private String mcChassisIdNumber;
    @SerializedName("mcGuaranteeStartDate")
    @Expose
    private String mcGuaranteeStartDate;
    @SerializedName("mcBuildYear")
    @Expose
    private String mcBuildYear;
    @SerializedName("mcLicensePlate_part1")
    @Expose
    private String mcLicensePlatePart1;
    @SerializedName("mcLicensePlate_part2")
    @Expose
    private String mcLicensePlatePart2;
    @SerializedName("mcLicensePlate_part3")
    @Expose
    private String mcLicensePlatePart3;
    @SerializedName("mcLicensePlate_part4")
    @Expose
    private String mcLicensePlatePart4;
    @SerializedName("mcLicensePlate_part5")
    @Expose
    private String mcLicensePlatePart5;
    @SerializedName("mcEngineIdNumber")
    @Expose
    private String mcEngineIdNumber;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product")
    @Expose
    private Product product;

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

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public String getMcChassisIdNumber() {
        return mcChassisIdNumber;
    }

    public void setMcChassisIdNumber(String mcChassisIdNumber) {
        this.mcChassisIdNumber = mcChassisIdNumber;
    }

    public String getMcGuaranteeStartDate() {
        return mcGuaranteeStartDate;
    }

    public void setMcGuaranteeStartDate(String mcGuaranteeStartDate) {
        this.mcGuaranteeStartDate = mcGuaranteeStartDate;
    }

    public String getMcBuildYear() {
        return mcBuildYear;
    }

    public void setMcBuildYear(String mcBuildYear) {
        this.mcBuildYear = mcBuildYear;
    }

    public String getMcLicensePlatePart1() {
        return mcLicensePlatePart1;
    }

    public void setMcLicensePlatePart1(String mcLicensePlatePart1) {
        this.mcLicensePlatePart1 = mcLicensePlatePart1;
    }

    public String getMcLicensePlatePart2() {
        return mcLicensePlatePart2;
    }

    public void setMcLicensePlatePart2(String mcLicensePlatePart2) {
        this.mcLicensePlatePart2 = mcLicensePlatePart2;
    }

    public String getMcLicensePlatePart3() {
        return mcLicensePlatePart3;
    }

    public void setMcLicensePlatePart3(String mcLicensePlatePart3) {
        this.mcLicensePlatePart3 = mcLicensePlatePart3;
    }

    public String getMcLicensePlatePart4() {
        return mcLicensePlatePart4;
    }

    public void setMcLicensePlatePart4(String mcLicensePlatePart4) {
        this.mcLicensePlatePart4 = mcLicensePlatePart4;
    }

    public String getMcLicensePlatePart5() {
        return mcLicensePlatePart5;
    }

    public void setMcLicensePlatePart5(String mcLicensePlatePart5) {
        this.mcLicensePlatePart5 = mcLicensePlatePart5;
    }

    public String getMcEngineIdNumber() {
        return mcEngineIdNumber;
    }

    public void setMcEngineIdNumber(String mcEngineIdNumber) {
        this.mcEngineIdNumber = mcEngineIdNumber;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
