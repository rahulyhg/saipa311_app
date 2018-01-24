
package key_team.com.saipa311.AfterSale_services.JsonSchema.Assistance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssistanceRequestParams {

    @SerializedName("repId")
    @Expose
    private Integer repId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
