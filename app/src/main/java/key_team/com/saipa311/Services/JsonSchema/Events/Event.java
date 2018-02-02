
package key_team.com.saipa311.Services.JsonSchema.Events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("eState")
    @Expose
    private Integer eState;
    @SerializedName("eSubject")
    @Expose
    private String eSubject;
    @SerializedName("eDescription")
    @Expose
    private String eDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("representation")
    @Expose
    private Representation representation;
    @SerializedName("event_img")
    @Expose
    private List<EventImg> eventImg = null;
    @SerializedName("viewed")
    @Expose
    private List<Viewed> viewed = null;

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

    public Integer getEState() {
        return eState;
    }

    public void setEState(Integer eState) {
        this.eState = eState;
    }

    public String getESubject() {
        return eSubject;
    }

    public void setESubject(String eSubject) {
        this.eSubject = eSubject;
    }

    public String getEDescription() {
        return eDescription;
    }

    public void setEDescription(String eDescription) {
        this.eDescription = eDescription;
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

    public List<EventImg> getEventImg() {
        return eventImg;
    }

    public void setEventImg(List<EventImg> eventImg) {
        this.eventImg = eventImg;
    }

    public List<Viewed> getViewed() {
        return viewed;
    }

    public void setViewed(List<Viewed> viewed) {
        this.viewed = viewed;
    }

}
