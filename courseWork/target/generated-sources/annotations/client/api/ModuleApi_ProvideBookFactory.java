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
public final class ModuleApi_ProvideBookFactory implements Factory<BookApi> {
  private final ModuleApi module;

  private final Provider<Retrofit> retrofitProvider;

  public ModuleApi_ProvideBookFactory(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public BookApi get() {
    return provideInstance(module, retrofitProvider);
  }

  public static BookApi provideInstance(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideBook(module, retrofitProvider.get());
  }

  public static ModuleApi_ProvideBookFactory create(
      ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return new ModuleApi_ProvideBookFactory(module, retrofitProvider);
  }

  public static BookApi proxyProvideBook(ModuleApi instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideBook(retrofit), "Cannot return null from a non-@Nullable @Provides method");
  }
}
