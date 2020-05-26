package client.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class ModuleApi {

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public BookApi provideBook(Retrofit retrofit) {
        return retrofit.create(BookApi.class);
    }

    @Provides
    @Singleton
    public BookTypeApi provideType(Retrofit retrofit) {
        return retrofit.create(BookTypeApi.class);
    }

    @Provides
    @Singleton
    public ClientApi provideClient(Retrofit retrofit) {
        return retrofit.create(ClientApi.class);
    }

    @Provides
    @Singleton
    public JournalApi provideJournal(Retrofit retrofit) {
        return retrofit.create(JournalApi.class);
    }

    @Provides
    @Singleton
    public Auth provideAuth(Retrofit retrofit) {
        return retrofit.create(Auth.class);
    }

}
