
package key_team.com.saipa311.Auth.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserActivationRequestParams {

    @SerializedName("activationCode")
    @Expose
    private String activationCode;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
