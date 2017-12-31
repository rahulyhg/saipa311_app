package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

/**
 * Created by ammorteza on 12/11/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCarRequestExists {
    @SerializedName("exist")
    @Expose
    private boolean exist;

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
