
package key_team.com.saipa311.Sale_services.JsonSchema.OldCars;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OldCar {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gId")
    @Expose
    private Integer gId;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("prId")
    @Expose
    private Integer prId;
    @SerializedName("ocState")
    @Expose
    private Integer ocState;
    @SerializedName("ocFuelType")
    @Expose
    private String ocFuelType;
    @SerializedName("ocPrice")
    @Expose
    private String ocPrice;
    @SerializedName("ocKmOfOperation")
    @Expose
    private String ocKmOfOperation;
    @SerializedName("ocBody")
    @Expose
    private String ocBody;
    @SerializedName("ocBuildYear")
    @Expose
    private String ocBuildYear;
    @SerializedName("ocColor")
    @Expose
    private String ocColor;
    @SerializedName("ocDescription")
    @Expose
    private String ocDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("old_car_image")
    @Expose
    private List<OldCarImage> oldCarImage = null;
    @SerializedName("gear_box")
    @Expose
    private GearBox gearBox;
    @SerializedName("product")
    @Expose
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGId() {
        return gId;
    }

    public void setGId(Integer gId) {
        this.gId = gId;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public Integer getOcState() {
        return ocState;
    }

    public void setOcState(Integer ocState) {
        this.ocState = ocState;
    }

    public String getOcFuelType() {
        return ocFuelType;
    }

    public void setOcFuelType(String ocFuelType) {
        this.ocFuelType = ocFuelType;
    }

    public String getOcPrice() {
        return ocPrice;
    }

    public void setOcPrice(String ocPrice) {
        this.ocPrice = ocPrice;
    }

    public String getOcKmOfOperation() {
        return ocKmOfOperation;
    }

    public void setOcKmOfOperation(String ocKmOfOperation) {
        this.ocKmOfOperation = ocKmOfOperation;
    }

    public String getOcBody() {
        return ocBody;
    }

    public void setOcBody(String ocBody) {
        this.ocBody = ocBody;
    }

    public String getOcBuildYear() {
        return ocBuildYear;
    }

    public void setOcBuildYear(String ocBuildYear) {
        this.ocBuildYear = ocBuildYear;
    }

    public String getOcColor() {
        return ocColor;
    }

    public void setOcColor(String ocColor) {
        this.ocColor = ocColor;
    }

    public String getOcDescription() {
        return ocDescription;
    }

    public void setOcDescription(String ocDescription) {
        this.ocDescription = ocDescription;
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

    public List<OldCarImage> getOldCarImage() {
        return oldCarImage;
    }

    public void setOldCarImage(List<OldCarImage> oldCarImage) {
        this.oldCarImage = oldCarImage;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
