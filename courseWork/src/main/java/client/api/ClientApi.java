package client.api;

import client.model.ClientModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ClientApi {
    @GET("clients")
    Observable<Response<List<ClientModel>>> getAllClients(@Header("Authorization") String token);

    @GET("clients/{id}")
    Observable<Response<ClientModel>> getClientById(@Header("Authorization") String token, @Path("id") long id);

    @POST("clients")
    Observable<Response<ClientModel>> createClient(@Header("Authorization") String token, @Body ClientModel clientModel);

    @PUT("clients/{id}")
    Observable<Response<Void>>  updateClient(@Header("Authorization") String token, @Path("id") long id, @Body ClientModel clientModel);

    @DELETE("clients/{id}")
    Observable<Response<Void>> deleteClient(@Header("Authorization") String token, @Path("id") long id);
}
