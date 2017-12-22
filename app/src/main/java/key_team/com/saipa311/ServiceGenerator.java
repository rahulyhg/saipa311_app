package key_team.com.saipa311;

import java.io.IOException;

import key_team.com.saipa311.DB_Management.UserInfo;
import key_team.com.saipa311.PublicParams;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ammorteza on 12/8/17.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = PublicParams.BASE_URL + "api/";
/*    private static Authenticator authenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + UserInfo.getUserInfo().access_token)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();

        }
    };*/
/*    private static OkHttpClient httpClient =
            new OkHttpClient.Builder()
                    .authenticator(authenticator)
                    .build();*/

    private static OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + (UserInfo.getUserInfo() == null ? "" : UserInfo.getUserInfo().access_token))
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
