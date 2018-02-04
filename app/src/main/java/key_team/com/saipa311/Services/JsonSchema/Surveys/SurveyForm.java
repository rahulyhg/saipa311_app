
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyForm {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uId")
    @Expose
    private Integer uId;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("sfViewed")
    @Expose
    private Integer sfViewed;
    @SerializedName("sfDelivery")
    @Expose
    private Integer sfDelivery;
    @SerializedName("sfEducation")
    @Expose
    private String sfEducation;
    @SerializedName("sfSubject")
    @Expose
    private String sfSubject;
    @SerializedName("sfSex")
    @Expose
    private String sfSex;
    @SerializedName("sfFuelType")
    @Expose
    private String sfFuelType;
    @SerializedName("sfIsSecondVisit")
    @Expose
    private String sfIsSecondVisit;
    @SerializedName("sfDescription")
    @Expose
    private String sfDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("representation")
    @Expose
    private Representation representation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getSfViewed() {
        return sfViewed;
    }

    public void setSfViewed(Integer sfViewed) {
        this.sfViewed = sfViewed;
    }

    public Integer getSfDelivery() {
        return sfDelivery;
    }

    public void setSfDelivery(Integer sfDelivery) {
        this.sfDelivery = sfDelivery;
    }

    public String getSfEducation() {
        return sfEducation;
    }

    public void setSfEducation(String sfEducation) {
        this.sfEducation = sfEducation;
    }

    public String getSfSubject() {
        return sfSubject;
    }

    public void setSfSubject(String sfSubject) {
        this.sfSubject = sfSubject;
    }

    public String getSfSex() {
        return sfSex;
    }

    public void setSfSex(String sfSex) {
        this.sfSex = sfSex;
    }

    public String getSfFuelType() {
        return sfFuelType;
    }

    public void setSfFuelType(String sfFuelType) {
        this.sfFuelType = sfFuelType;
    }

    public String getSfIsSecondVisit() {
        return sfIsSecondVisit;
    }

    public void setSfIsSecondVisit(String sfIsSecondVisit) {
        this.sfIsSecondVisit = sfIsSecondVisit;
    }

    public String getSfDescription() {
        return sfDescription;
    }

    public void setSfDescription(String sfDescription) {
        this.sfDescription = sfDescription;
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

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

}
