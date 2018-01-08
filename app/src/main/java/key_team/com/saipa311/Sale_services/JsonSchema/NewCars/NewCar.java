
package key_team.com.saipa311.Sale_services.JsonSchema.NewCars;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCar {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ncState")
    @Expose
    private Integer ncState;
    @SerializedName("ncPrice")
    @Expose
    private String ncPrice;
    @SerializedName("ncConditions")
    @Expose
    private Integer ncConditions;
    @SerializedName("ncTerms_of_sale_img")
    @Expose
    private String ncTermsOfSaleImg;
    @SerializedName("ncWheelbase")
    @Expose
    private String ncWheelbase;
    @SerializedName("ncLength")
    @Expose
    private String ncLength;
    @SerializedName("ncWidth")
    @Expose
    private String ncWidth;
    @SerializedName("ncHeight")
    @Expose
    private String ncHeight;
    @SerializedName("ncAcceleration")
    @Expose
    private String ncAcceleration;
    @SerializedName("ncLoad_space")
    @Expose
    private String ncLoadSpace;
    @SerializedName("ncEngine")
    @Expose
    private String ncEngine;
    @SerializedName("ncMax_speed")
    @Expose
    private String ncMaxSpeed;
    @SerializedName("ncMax_power")
    @Expose
    private String ncMaxPower;
    @SerializedName("ncBrake_system")
    @Expose
    private String ncBrakeSystem;
    @SerializedName("ncTire")
    @Expose
    private String ncTire;
    @SerializedName("ncGvm")
    @Expose
    private String ncGvm;
    @SerializedName("ncFuel_tank")
    @Expose
    private String ncFuelTank;
    @SerializedName("ncMax_torque")
    @Expose
    private String ncMaxTorque;
    @SerializedName("ncFuel_efficiency")
    @Expose
    private String ncFuelEfficiency;
    @SerializedName("ncPollution_standard")
    @Expose
    private String ncPollutionStandard;
    @SerializedName("ncCylinder")
    @Expose
    private String ncCylinder;
    @SerializedName("ncTransmission_type")
    @Expose
    private String ncTransmissionType;
    @SerializedName("ncDescription")
    @Expose
    private String ncDescription;
    @SerializedName("cId")
    @Expose
    private Integer cId;
    @SerializedName("gId")
    @Expose
    private Integer gId;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("prId")
    @Expose
    private Integer prId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("representation")
    @Expose
    private Representation representation;
    @SerializedName("new_car_image")
    @Expose
    private List<NewCarImage> newCarImage = null;
    @SerializedName("chassis")
    @Expose
    private Chassis chassis;
    @SerializedName("gear_box")
    @Expose
    private GearBox gearBox;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("new_car_color")
    @Expose
    private List<NewCarColor> newCarColor = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNcState() {
        return ncState;
    }

    public void setNcState(Integer ncState) {
        this.ncState = ncState;
    }

    public String getNcPrice() {
        return ncPrice;
    }

    public void setNcPrice(String ncPrice) {
        this.ncPrice = ncPrice;
    }

    public Integer getNcConditions() {
        return ncConditions;
    }

    public void setNcConditions(Integer ncConditions) {
        this.ncConditions = ncConditions;
    }

    public String getNcTermsOfSaleImg() {
        return ncTermsOfSaleImg;
    }

    public void setNcTermsOfSaleImg(String ncTermsOfSaleImg) {
        this.ncTermsOfSaleImg = ncTermsOfSaleImg;
    }

    public String getNcWheelbase() {
        return ncWheelbase;
    }

    public void setNcWheelbase(String ncWheelbase) {
        this.ncWheelbase = ncWheelbase;
    }

    public String getNcLength() {
        return ncLength;
    }

    public void setNcLength(String ncLength) {
        this.ncLength = ncLength;
    }

    public String getNcWidth() {
        return ncWidth;
    }

    public void setNcWidth(String ncWidth) {
        this.ncWidth = ncWidth;
    }

    public String getNcHeight() {
        return ncHeight;
    }

    public void setNcHeight(String ncHeight) {
        this.ncHeight = ncHeight;
    }

    public String getNcAcceleration() {
        return ncAcceleration;
    }

    public void setNcAcceleration(String ncAcceleration) {
        this.ncAcceleration = ncAcceleration;
    }

    public String getNcLoadSpace() {
        return ncLoadSpace;
    }

    public void setNcLoadSpace(String ncLoadSpace) {
        this.ncLoadSpace = ncLoadSpace;
    }

    public String getNcEngine() {
        return ncEngine;
    }

    public void setNcEngine(String ncEngine) {
        this.ncEngine = ncEngine;
    }

    public String getNcMaxSpeed() {
        return ncMaxSpeed;
    }

    public void setNcMaxSpeed(String ncMaxSpeed) {
        this.ncMaxSpeed = ncMaxSpeed;
    }

    public String getNcMaxPower() {
        return ncMaxPower;
    }

    public void setNcMaxPower(String ncMaxPower) {
        this.ncMaxPower = ncMaxPower;
    }

    public String getNcBrakeSystem() {
        return ncBrakeSystem;
    }

    public void setNcBrakeSystem(String ncBrakeSystem) {
        this.ncBrakeSystem = ncBrakeSystem;
    }

    public String getNcTire() {
        return ncTire;
    }

    public void setNcTire(String ncTire) {
        this.ncTire = ncTire;
    }

    public String getNcGvm() {
        return ncGvm;
    }

    public void setNcGvm(String ncGvm) {
        this.ncGvm = ncGvm;
    }

    public String getNcFuelTank() {
        return ncFuelTank;
    }

    public void setNcFuelTank(String ncFuelTank) {
        this.ncFuelTank = ncFuelTank;
    }

    public String getNcMaxTorque() {
        return ncMaxTorque;
    }

    public void setNcMaxTorque(String ncMaxTorque) {
        this.ncMaxTorque = ncMaxTorque;
    }

    public String getNcFuelEfficiency() {
        return ncFuelEfficiency;
    }

    public void setNcFuelEfficiency(String ncFuelEfficiency) {
        this.ncFuelEfficiency = ncFuelEfficiency;
    }

    public String getNcPollutionStandard() {
        return ncPollutionStandard;
    }

    public void setNcPollutionStandard(String ncPollutionStandard) {
        this.ncPollutionStandard = ncPollutionStandard;
    }

    public String getNcCylinder() {
        return ncCylinder;
    }

    public void setNcCylinder(String ncCylinder) {
        this.ncCylinder = ncCylinder;
    }

    public String getNcTransmissionType() {
        return ncTransmissionType;
    }

    public void setNcTransmissionType(String ncTransmissionType) {
        this.ncTransmissionType = ncTransmissionType;
    }

    public String getNcDescription() {
        return ncDescription;
    }

    public void setNcDescription(String ncDescription) {
        this.ncDescription = ncDescription;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
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

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    public List<NewCarImage> getNewCarImage() {
        return newCarImage;
    }

    public void setNewCarImage(List<NewCarImage> newCarImage) {
        this.newCarImage = newCarImage;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
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

    public List<NewCarColor> getNewCarColor() {
        return newCarColor;
    }

    public void setNewCarColor(List<NewCarColor> newCarColor) {
        this.newCarColor = newCarColor;
    }

}
