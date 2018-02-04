
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyQuestion {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sqState")
    @Expose
    private Integer sqState;
    @SerializedName("sqSubject")
    @Expose
    private String sqSubject;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("survey_question_form")
    @Expose
    private List<SurveyQuestionForm> surveyQuestionForm = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSqState() {
        return sqState;
    }

    public void setSqState(Integer sqState) {
        this.sqState = sqState;
    }

    public String getSqSubject() {
        return sqSubject;
    }

    public void setSqSubject(String sqSubject) {
        this.sqSubject = sqSubject;
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

    public List<SurveyQuestionForm> getSurveyQuestionForm() {
        return surveyQuestionForm;
    }

    public void setSurveyQuestionForm(List<SurveyQuestionForm> surveyQuestionForm) {
        this.surveyQuestionForm = surveyQuestionForm;
    }

}
