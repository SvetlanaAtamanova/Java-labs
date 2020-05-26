package client.api;

import client.model.UserModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Auth {
    @POST("auth/signin")
    Observable<Response<String>> singIn(@Body UserModel user);
}
