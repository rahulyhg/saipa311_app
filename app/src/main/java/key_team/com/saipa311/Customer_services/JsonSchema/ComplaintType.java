
package key_team.com.saipa311.Customer_services.JsonSchema;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintType {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ctSubject")
    @Expose
    private String ctSubject;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("complaint")
    @Expose
    private List<Complaint> complaint = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCtSubject() {
        return ctSubject;
    }

    public void setCtSubject(String ctSubject) {
        this.ctSubject = ctSubject;
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

    public List<Complaint> getComplaint() {
        return complaint;
    }

    public void setComplaint(List<Complaint> complaint) {
        this.complaint = complaint;
    }

}
