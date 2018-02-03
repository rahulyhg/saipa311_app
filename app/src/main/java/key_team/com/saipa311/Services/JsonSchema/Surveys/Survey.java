
package key_team.com.saipa311.Services.JsonSchema.Surveys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Survey {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("representation")
    @Expose
    private Representation representation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

}
