
package key_team.com.saipa311.Auth.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewActivationCodeRequestParams {

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
