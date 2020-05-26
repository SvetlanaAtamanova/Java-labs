package client.api;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ModuleApi_ProvideClientFactory implements Factory<ClientApi> {
  private final ModuleApi module;

  private final Provider<Retrofit> retrofitProvider;

  public ModuleApi_ProvideClientFactory(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ClientApi get() {
    return provideInstance(module, retrofitProvider);
  }

  public static ClientApi provideInstance(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideClient(module, retrofitProvider.get());
  }

  public static ModuleApi_ProvideClientFactory create(
      ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return new ModuleApi_ProvideClientFactory(module, retrofitProvider);
  }

  public static ClientApi proxyProvideClient(ModuleApi instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideClient(retrofit),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
