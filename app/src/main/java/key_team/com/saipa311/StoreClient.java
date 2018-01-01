package key_team.com.saipa311;

import java.util.List;

import key_team.com.saipa311.Auth.JsonSchema.RefreshTokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.RegisterUserResult;
import key_team.com.saipa311.Auth.JsonSchema.TokenInfo;
import key_team.com.saipa311.Auth.JsonSchema.TokenRequestParams;
import key_team.com.saipa311.Auth.JsonSchema.User;
import key_team.com.saipa311.Auth.JsonSchema.UserActivationRequestParams;
import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.Deposit;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExists;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestExistsParams;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestParams;
import key_team.com.saipa311.Sale_services.JsonSchema.Deposits.DepositRequestRequestParams;
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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ammorteza on 12/8/17.
 */
public interface StoreClient {
    UserInfo userInformation = new UserInfo();
    @POST("app/sale_service/new_car/fetchAllData")
    Call<List<NewCar>> fetchNewCars(@Body NewCarRequestParams params);

    @POST("app/sale_service/old_car/fetchAllData")
    Call<List<OldCar>> fetchOldCars(@Body OldCarRequestParams params);

    @POST("app/sale_service/deposit/fetchAllData")
    Call<List<Deposit>> fetchDeposits(@Body DepositRequestParams params);

    @POST("login")
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

}
