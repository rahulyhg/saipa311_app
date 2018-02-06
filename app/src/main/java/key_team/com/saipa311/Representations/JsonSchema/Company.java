
package key_team.com.saipa311.Representations.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coSubject")
    @Expose
    private String coSubject;
    @SerializedName("coIconPath")
    @Expose
    private String coIconPath;
    @SerializedName("coDescription")
    @Expose
    private String coDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoSubject() {
        return coSubject;
    }

    public void setCoSubject(String coSubject) {
        this.coSubject = coSubject;
    }

    public String getCoIconPath() {
        return coIconPath;
    }

    public void setCoIconPath(String coIconPath) {
        this.coIconPath = coIconPath;
    }

    public String getCoDescription() {
        return coDescription;
    }

    public void setCoDescription(String coDescription) {
        this.coDescription = coDescription;
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

}
