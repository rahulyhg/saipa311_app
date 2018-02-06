
package key_team.com.saipa311.Representations.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Province {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pName")
    @Expose
    private String pName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

}
