
package key_team.com.saipa311.Representations.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class County {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cPId")
    @Expose
    private Integer cPId;
    @SerializedName("cName")
    @Expose
    private String cName;
    @SerializedName("province")
    @Expose
    private Province province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCPId() {
        return cPId;
    }

    public void setCPId(Integer cPId) {
        this.cPId = cPId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

}
