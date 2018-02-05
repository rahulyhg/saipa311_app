
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedUserSurveyAnswer {

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
