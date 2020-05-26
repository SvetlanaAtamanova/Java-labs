package client.api;

import io.reactivex.Observable;
import client.model.JournalModel;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface JournalApi {
    @GET("journal")
    Observable<Response<List<JournalModel>>> getAllJournal(@Header("Authorization") String token);

    @GET("journal/{id}")
    Observable<Response<JournalModel>> getJournalById(@Header("Authorization") String token, @Path("id") long id);

    @GET("journal/books/{id}")
    Observable<Response<JournalModel>> getBookId(@Header("Authorization") String token, @Path("id") long id);

    @GET("journal/clients/{id}")
    Observable<Response<JournalModel>> getClientId(@Header("Authorization") String token, @Path("id") long id);

    @POST("journal")
    Observable<Response<JournalModel>> createJournal(@Header("Authorization") String token, @Body JournalModel journalModel);

    @PUT("journal/{id}")
    Observable<Response<Void>>  updateJournal(@Header("Authorization") String token, @Path("id") long id, @Body JournalModel journalModel);

    @DELETE("journal/{id}")
    Observable<Response<Void>> deleteJournal(@Header("Authorization") String token, @Path("id") long id);
}
