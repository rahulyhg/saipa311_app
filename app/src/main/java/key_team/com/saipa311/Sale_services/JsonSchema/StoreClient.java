package key_team.com.saipa311.Sale_services.JsonSchema;

import java.util.List;

import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCar;
import key_team.com.saipa311.Sale_services.JsonSchema.NewCars.NewCarRequestParams;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ammorteza on 12/8/17.
 */
public interface StoreClient {
    @POST("sale_service/new_car/fetchAllDate")
    Call<List<NewCar>> fetchNewCars(@Body NewCarRequestParams params);
}
