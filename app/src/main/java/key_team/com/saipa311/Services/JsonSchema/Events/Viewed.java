
package key_team.com.saipa311.Services.JsonSchema.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Viewed {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eId")
    @Expose
    private Integer eId;
    @SerializedName("edViewed")
    @Expose
    private Integer edViewed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEId() {
        return eId;
    }

    public void setEId(Integer eId) {
        this.eId = eId;
    }

    public Integer getEdViewed() {
        return edViewed;
    }

    public void setEdViewed(Integer edViewed) {
        this.edViewed = edViewed;
    }

}
