
package key_team.com.saipa311.Sale_services.JsonSchema.Deposits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deposit {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dState")
    @Expose
    private Integer dState;
    @SerializedName("reId")
    @Expose
    private Integer reId;
    @SerializedName("dCar")
    @Expose
    private String dCar;
    @SerializedName("dPrePaymentAmount")
    @Expose
    private String dPrePaymentAmount;
    @SerializedName("dThreeMonthProfit")
    @Expose
    private String dThreeMonthProfit;
    @SerializedName("dSixMonthProfit")
    @Expose
    private String dSixMonthProfit;
    @SerializedName("dNineMonthProfit")
    @Expose
    private String dNineMonthProfit;
    @SerializedName("dTwelveMonthProfit")
    @Expose
    private String dTwelveMonthProfit;
    @SerializedName("dNotificationId")
    @Expose
    private String dNotificationId;
    @SerializedName("dDescription")
    @Expose
    private String dDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDState() {
        return dState;
    }

    public void setDState(Integer dState) {
        this.dState = dState;
    }

    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    public String getDCar() {
        return dCar;
    }

    public void setDCar(String dCar) {
        this.dCar = dCar;
    }

    public String getDPrePaymentAmount() {
        return dPrePaymentAmount;
    }

    public void setDPrePaymentAmount(String dPrePaymentAmount) {
        this.dPrePaymentAmount = dPrePaymentAmount;
    }

    public String getDThreeMonthProfit() {
        return dThreeMonthProfit;
    }

    public void setDThreeMonthProfit(String dThreeMonthProfit) {
        this.dThreeMonthProfit = dThreeMonthProfit;
    }

    public String getDSixMonthProfit() {
        return dSixMonthProfit;
    }

    public void setDSixMonthProfit(String dSixMonthProfit) {
        this.dSixMonthProfit = dSixMonthProfit;
    }

    public String getDNineMonthProfit() {
        return dNineMonthProfit;
    }

    public void setDNineMonthProfit(String dNineMonthProfit) {
        this.dNineMonthProfit = dNineMonthProfit;
    }

    public String getDTwelveMonthProfit() {
        return dTwelveMonthProfit;
    }

    public void setDTwelveMonthProfit(String dTwelveMonthProfit) {
        this.dTwelveMonthProfit = dTwelveMonthProfit;
    }

    public String getDNotificationId() {
        return dNotificationId;
    }

    public void setDNotificationId(String dNotificationId) {
        this.dNotificationId = dNotificationId;
    }

    public String getDDescription() {
        return dDescription;
    }

    public void setDDescription(String dDescription) {
        this.dDescription = dDescription;
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

}
