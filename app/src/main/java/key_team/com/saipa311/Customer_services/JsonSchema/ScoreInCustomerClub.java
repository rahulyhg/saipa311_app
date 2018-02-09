
package key_team.com.saipa311.Customer_services.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreInCustomerClub {

    @SerializedName("score")
    @Expose
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
