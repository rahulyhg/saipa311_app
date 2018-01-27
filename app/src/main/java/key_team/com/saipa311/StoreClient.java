package key_team.com.saipa311;

import java.util.List;
import java.util.Map;

import key_team.com.saipa311.AfterSale_services.JsonSchema.Assistance.AssistanceRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCard;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExists;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestExistsParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.GoldCards.GoldCardRequestRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCar;
import key_team.com.saipa311.AfterSale_services.JsonSchema.MyCars.MyCarsRegisterParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionList;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionListRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.AdmissionServiceType;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.DeclarationGroup;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TheTurnRequestParams;
import key_team.com.saipa311.AfterSale_services.JsonSchema.Turning.TrackingCode;
import key_team.com.saipa311.Auth.JsonSchema.NewActivationCodeRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RefreshTokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserResult;
import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.Auth.JsonSchema.UserActivationRequestParams;
import key_team.com.saipa311.Customer_services.JsonSchema.CriticismAndSuggestionRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.CompanyWithProduct;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.ExchangeRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.Exchange.ExchangeRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarOption;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarOptionsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCar;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OldCars.OldCarRequestRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlans;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarChangePlanRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.OutDatedCar.OutDatedCarRequestExistsParams;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by ammorteza on 12/8/17.
 */
public interface StoreClient {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @POST("app/sale_service/new_car/fetchAllData")
    Call<List<NewCar>> fetchNewCars(@Body NewCarRequestParams params);

    @POST("app/sale_service/old_car/fetchAllData")
    Call<List<OldCar>> fetchOldCars(@Body OldCarRequestParams params);

    @POST("app/sale_service/deposit/fetchAllData")
    Call<List<Deposit>> fetchDeposits(@Body DepositRequestParams params);

    @POST("app/login")
    Call<TokenInfo> login(@Body TokenRequestParams params);

    @POST("refresh_token")
    Call<TokenInfo> refreshToken(@Body RefreshTokenRequestParams params);

    @POST("user_info")
    Call<User> userInfo();

    @POST("app/register_customer_user")
    Call<RegisterUserResult> registerUser(@Body RegisterUserRequestParams params);

    @POST("app/active_customer_user")
    Call<Void> userActivation(@Body UserActivationRequestParams params);

    @POST("app/sale_service/new_car/registerRequest")
    Call<Void> registerNewCarRequest(@Body NewCarRequestRequestParams params);

    @POST("app/sale_service/old_car/registerRequest")
    Call<Void> registerOldCarRequest(@Body OldCarRequestRequestParams params);

    @POST("app/option/fetchOptionWithRepAndPid")
    Call<List<NewCarOption>> fetchOptionWithRepAndPid(@Body NewCarOptionsParams params);

    @POST("app/sale_service/deposit/registerRequest")
    Call<Void> registerDepositRequest(@Body DepositRequestRequestParams params);

    @POST("app/sale_service/new_car/isNotTrackedRequestExist")
    Call<NewCarRequestExists> isNotTrackedRequestExist(@Body NewCarRequestExistsParams params);

    @POST("app/sale_service/deposit/isNotTrackedRequestExist")
    Call<DepositRequestExists> isNotTrackedDepositRequestExist(@Body DepositRequestExistsParams params);

    @POST("app/sale_service/old_car/isNotTrackedRequestExist")
    Call<OldCarRequestExists> isNotTrackedOldCarRequestExist(@Body OldCarRequestExistsParams params);

    @POST("company/getAllCompanyWithProducts")
    Call<List<CompanyWithProduct>> getAllCompanyWithProducts();

    @Multipart
    @POST("app/sale_service/exchange/registerRequest")
    Call<Void> registerExchangeRequest(
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part[] files);

    @POST("app/sale_service/exchange/isNotTrackedRequestExist")
    Call<ExchangeRequestExists> isNotTrackedExchangeRequestExist(@Body ExchangeRequestExistsParams params);

    @POST("app/sale_service/outDatedCar/plan/fetchActivePlan")
    Call<OutDatedCarChangePlans> getOutDatedCarChangeActivePlan(@Body OutDatedCarChangePlanRequestParams params);

    @POST("app/sale_service/outDatedCar/isNotTrackedRequestExist")
    Call<OutDatedCarRequestExists> isNotTrackedODCCRequestExist(@Body OutDatedCarRequestExistsParams params);

    @Multipart
    @POST("app/sale_service/outDatedCar/registerRequest")
    Call<Void> registerOutDatedCarChangeRequest(
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part[] files);

    @POST("app/after_sale_service/gold_card/fetchAllData")
    Call<List<GoldCard>> fetchGoldCards(@Body GoldCardRequestParams params);

    @POST("app/after_sale_service/gold_card/registerRequest")
    Call<Void> registerGoldCardRequest(@Body GoldCardRequestRequestParams params);

    @POST("app/after_sale_service/gold_card/isNotTrackedRequestExist")
    Call<GoldCardRequestExists> isNotTrackedGoldCardRequestExist(@Body GoldCardRequestExistsParams params);

    @POST("app/after_sale_service/my_car/register")
    Call<List<MyCar>> registerMyCar(@Body MyCarsRegisterParams params);

    @POST("app/after_sale_service/my_car/fetchAllData")
    Call<List<MyCar>> fetchMyCars();

    @POST("app/after_sale_service/admission_capacity/getAllTimesForMyCarWithRepId")
    Call<List<AdmissionList>> getAllTimesForMyCarWithRepId(@Body AdmissionListRequestParams params);

    @POST("admissionServiceType/getAllType")
    Call<List<AdmissionServiceType>> getAllAdmissionServiceType();

    @POST("app/after_sale_service/admission_capacity/registerTheTurn")
    Call<TrackingCode> registerTheTurn(@Body TheTurnRequestParams params);

    @POST("app/after_sale_service/assistance_request/registerRequest")
    Call<TrackingCode> registerAssistanceRequest(@Body AssistanceRequestParams params);

    @POST("app/get_customer_user_active_code")
    Call<Void> getNewActivationCode(@Body NewActivationCodeRequestParams params);

    @POST("app/customer_service/criticism_and_suggestion/registerCriticismAndSuggestion")
    Call<Void> registerCriticismAndSuggestion(@Body CriticismAndSuggestionRequestParams params);
}
