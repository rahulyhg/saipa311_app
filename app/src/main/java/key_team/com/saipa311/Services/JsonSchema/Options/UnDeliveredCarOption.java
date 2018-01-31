
package key_team.com.saipa311.Services.JsonSchema.Options;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnDeliveredCarOption {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coState")
    @Expose
    private Integer coState;
    @SerializedName("coOId")
    @Expose
    private Integer coOId;
    @SerializedName("coPId")
    @Expose
    private Integer coPId;
    @SerializedName("coReId")
    @Expose
    private Integer coReId;
    @SerializedName("coImgPath")
    @Expose
    private String coImgPath;
    @SerializedName("coPrice")
    @Expose
    private String coPrice;
    @SerializedName("coDescription")
    @Expose
    private String coDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("option")
    @Expose
    private Option option;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("representation")
    @Expose
    private Representation representation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoState() {
        return coState;
    }

    public void setCoState(Integer coState) {
        this.coState = coState;
    }

    public Integer getCoOId() {
        return coOId;
    }

    public void setCoOId(Integer coOId) {
        this.coOId = coOId;
    }

    public Integer getCoPId() {
        return coPId;
    }

    public void setCoPId(Integer coPId) {
        this.coPId = coPId;
    }

    public Integer getCoReId() {
        return coReId;
    }

    public void setCoReId(Integer coReId) {
        this.coReId = coReId;
    }

    public String getCoImgPath() {
        return coImgPath;
    }

    public void setCoImgPath(String coImgPath) {
        this.coImgPath = coImgPath;
    }

    public String getCoPrice() {
        return coPrice;
    }

    public void setCoPrice(String coPrice) {
        this.coPrice = coPrice;
    }

    public String getCoDescription() {
        return coDescription;
    }

    public void setCoDescription(String coDescription) {
        this.coDescription = coDescription;
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

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

}
