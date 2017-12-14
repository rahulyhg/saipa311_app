
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Representation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rDescription")
    @Expose
    private String rDescription;
    @SerializedName("rName")
    @Expose
    private String rName;
    @SerializedName("rCode")
    @Expose
    private String rCode;
    @SerializedName("rAddress")
    @Expose
    private String rAddress;
    @SerializedName("tId")
    @Expose
    private Integer tId;
    @SerializedName("lId")
    @Expose
    private Integer lId;
    @SerializedName("gId")
    @Expose
    private Integer gId;
    @SerializedName("cId")
    @Expose
    private Integer cId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("level")
    @Expose
    private Level level;
    @SerializedName("grade")
    @Expose
    private Grade grade;
    @SerializedName("tell")
    @Expose
    private List<Tell> tell = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRDescription() {
        return rDescription;
    }

    public void setRDescription(String rDescription) {
        this.rDescription = rDescription;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getRCode() {
        return rCode;
    }

    public void setRCode(String rCode) {
        this.rCode = rCode;
    }

    public String getRAddress() {
        return rAddress;
    }

    public void setRAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public Integer getLId() {
        return lId;
    }

    public void setLId(Integer lId) {
        this.lId = lId;
    }

    public Integer getGId() {
        return gId;
    }

    public void setGId(Integer gId) {
        this.gId = gId;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Tell> getTell() {
        return tell;
    }

    public void setTell(List<Tell> tell) {
        this.tell = tell;
    }

}
