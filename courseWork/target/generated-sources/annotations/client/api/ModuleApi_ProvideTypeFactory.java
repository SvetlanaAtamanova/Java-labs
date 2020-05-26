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
public final class ModuleApi_ProvideTypeFactory implements Factory<BookTypeApi> {
  private final ModuleApi module;

  private final Provider<Retrofit> retrofitProvider;

  public ModuleApi_ProvideTypeFactory(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public BookTypeApi get() {
    return provideInstance(module, retrofitProvider);
  }

  public static BookTypeApi provideInstance(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideType(module, retrofitProvider.get());
  }

  public static ModuleApi_ProvideTypeFactory create(
      ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return new ModuleApi_ProvideTypeFactory(module, retrofitProvider);
  }

  public static BookTypeApi proxyProvideType(ModuleApi instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideType(retrofit), "Cannot return null from a non-@Nullable @Provides method");
  }
}
