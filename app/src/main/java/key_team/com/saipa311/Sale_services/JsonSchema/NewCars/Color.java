
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cSubject")
    @Expose
    private String cSubject;
    @SerializedName("cHexNumber")
    @Expose
    private String cHexNumber;
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

    public String getCSubject() {
        return cSubject;
    }

    public void setCSubject(String cSubject) {
        this.cSubject = cSubject;
    }

    public String getCHexNumber() {
        return cHexNumber;
    }

    public void setCHexNumber(String cHexNumber) {
        this.cHexNumber = cHexNumber;
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
