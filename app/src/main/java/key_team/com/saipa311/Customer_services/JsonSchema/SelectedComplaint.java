
package key_team.com.saipa311.Customer_services.JsonSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedComplaint {

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
