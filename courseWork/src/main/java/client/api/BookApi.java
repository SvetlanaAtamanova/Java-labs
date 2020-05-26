package client.api;

import client.model.BookModel;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface BookApi {
    @GET("books")
    Observable<Response<List<BookModel>>> getAllBooks(@Header("Authorization") String token);

    @GET("books/{id}")
    Observable<Response<BookModel>> getBookById(@Header("Authorization") String token, @Path("id") long id);

    @GET("books/bookTypes/{id}")
    Observable<Response<BookModel>> getTypeId(@Header("Authorization") String token, @Path("id") long id);

    @POST("books")
    Observable<Response<BookModel>> createBook(@Header("Authorization") String token, @Body BookModel bookModel);

    @PUT("books/{id}")
    Observable<Response<Void>>  updateBook(@Header("Authorization") String token, @Path("id") long id, @Body BookModel bookModel);

    @DELETE("books/{id}")
    Observable<Response<Void>> deleteBook(@Header("Authorization") String token, @Path("id") long id);
}
