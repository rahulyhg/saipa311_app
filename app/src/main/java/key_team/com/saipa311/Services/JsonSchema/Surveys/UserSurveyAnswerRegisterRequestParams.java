
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSurveyAnswerRegisterRequestParams {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("fuelType")
    @Expose
    private String fuelType;
    @SerializedName("secondVisit")
    @Expose
    private String secondVisit;
    @SerializedName("selectedCauseOfReferrals")
    @Expose
    private List<SelectedCauseOfReferral> selectedCauseOfReferrals = null;
    @SerializedName("selectedUserSurveyAnswers")
    @Expose
    private List<SelectedUserSurveyAnswer> selectedUserSurveyAnswers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getSecondVisit() {
        return secondVisit;
    }

    public void setSecondVisit(String secondVisit) {
        this.secondVisit = secondVisit;
    }

    public List<SelectedCauseOfReferral> getSelectedCauseOfReferrals() {
        return selectedCauseOfReferrals;
    }

    public void setSelectedCauseOfReferrals(List<SelectedCauseOfReferral> selectedCauseOfReferrals) {
        this.selectedCauseOfReferrals = selectedCauseOfReferrals;
    }

    public List<SelectedUserSurveyAnswer> getSelectedUserSurveyAnswers() {
        return selectedUserSurveyAnswers;
    }

    public void setSelectedUserSurveyAnswers(List<SelectedUserSurveyAnswer> selectedUserSurveyAnswers) {
        this.selectedUserSurveyAnswers = selectedUserSurveyAnswers;
    }

}
