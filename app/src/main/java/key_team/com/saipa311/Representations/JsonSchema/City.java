
package key_team.com.saipa311.Representations.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cPId")
    @Expose
    private Integer cPId;
    @SerializedName("cCId")
    @Expose
    private Integer cCId;
    @SerializedName("cName")
    @Expose
    private String cName;
    @SerializedName("cExistTPX")
    @Expose
    private Integer cExistTPX;
    @SerializedName("county")
    @Expose
    private County county;

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

    public Integer getCCId() {
        return cCId;
    }

    public void setCCId(Integer cCId) {
        this.cCId = cCId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public Integer getCExistTPX() {
        return cExistTPX;
    }

    public void setCExistTPX(Integer cExistTPX) {
        this.cExistTPX = cExistTPX;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

}
