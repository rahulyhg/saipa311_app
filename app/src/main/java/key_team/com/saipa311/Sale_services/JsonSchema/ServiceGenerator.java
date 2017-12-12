package key_team.com.saipa311.Sale_services.JsonSchema;

import key_team.com.saipa311.PublicParams;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ammorteza on 12/8/17.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = PublicParams.BASE_URL + "api/app/";

    private static OkHttpClient httpClient =
            new OkHttpClient.Builder().build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
