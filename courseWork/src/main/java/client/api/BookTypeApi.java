package client.api;

import client.model.BookTypeModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface BookTypeApi {
    @GET("bookTypes")
    Observable<Response<List<BookTypeModel>>> getAllTypes(@Header("Authorization") String token);

    @GET("bookTypes/{id}")
    Observable<Response<BookTypeModel>> getTypeById(@Header("Authorization") String token, @Path("id") long id);

    @POST("bookTypes")
    Observable<Response<BookTypeModel>> createType(@Header("Authorization") String token, @Body BookTypeModel bookTypeModel);

    @PUT("bookTypes/{id}")
    Observable<Response<Void>>  updateType(@Header("Authorization") String token, @Path("id") long id, @Body BookTypeModel bookTypeModel);

    @DELETE("bookTypes/{id}")
    Observable<Response<Void>> deleteType(@Header("Authorization") String token, @Path("id") long id);
}
