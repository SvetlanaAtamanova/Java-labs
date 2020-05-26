package client.api;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ModuleApi_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final ModuleApi module;

  private final Provider<Gson> gsonProvider;

  public ModuleApi_ProvideRetrofitFactory(ModuleApi module, Provider<Gson> gsonProvider) {
    this.module = module;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public Retrofit get() {
    return provideInstance(module, gsonProvider);
  }

  public static Retrofit provideInstance(ModuleApi module, Provider<Gson> gsonProvider) {
    return proxyProvideRetrofit(module, gsonProvider.get());
  }

  public static ModuleApi_ProvideRetrofitFactory create(
      ModuleApi module, Provider<Gson> gsonProvider) {
    return new ModuleApi_ProvideRetrofitFactory(module, gsonProvider);
  }

  public static Retrofit proxyProvideRetrofit(ModuleApi instance, Gson gson) {
    return Preconditions.checkNotNull(
        instance.provideRetrofit(gson), "Cannot return null from a non-@Nullable @Provides method");
  }
}
