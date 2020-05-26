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
public final class ModuleApi_ProvideAuthFactory implements Factory<Auth> {
  private final ModuleApi module;

  private final Provider<Retrofit> retrofitProvider;

  public ModuleApi_ProvideAuthFactory(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public Auth get() {
    return provideInstance(module, retrofitProvider);
  }

  public static Auth provideInstance(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideAuth(module, retrofitProvider.get());
  }

  public static ModuleApi_ProvideAuthFactory create(
      ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return new ModuleApi_ProvideAuthFactory(module, retrofitProvider);
  }

  public static Auth proxyProvideAuth(ModuleApi instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideAuth(retrofit), "Cannot return null from a non-@Nullable @Provides method");
  }
}
