package key_team.com.saipa311.Sale_services.JsonSchema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ammorteza on 12/8/17.
 */
public interface StoreClient {
    @POST("test")
    Call<Test> myTest(@Body Test test);
}
