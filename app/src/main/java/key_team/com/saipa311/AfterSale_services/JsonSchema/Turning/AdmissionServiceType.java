
package key_team.com.saipa311.AfterSale_services.JsonSchema.Turning;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdmissionServiceType {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("astSubject")
    @Expose
    private String astSubject;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("declaration_group")
    @Expose
    private List<DeclarationGroup> declarationGroup = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAstSubject() {
        return astSubject;
    }

    public void setAstSubject(String astSubject) {
        this.astSubject = astSubject;
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

    public List<DeclarationGroup> getDeclarationGroup() {
        return declarationGroup;
    }

    public void setDeclarationGroup(List<DeclarationGroup> declarationGroup) {
        this.declarationGroup = declarationGroup;
    }

}
