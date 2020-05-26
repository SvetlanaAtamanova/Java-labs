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
public final class ModuleApi_ProvideJournalFactory implements Factory<JournalApi> {
  private final ModuleApi module;

  private final Provider<Retrofit> retrofitProvider;

  public ModuleApi_ProvideJournalFactory(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public JournalApi get() {
    return provideInstance(module, retrofitProvider);
  }

  public static JournalApi provideInstance(ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideJournal(module, retrofitProvider.get());
  }

  public static ModuleApi_ProvideJournalFactory create(
      ModuleApi module, Provider<Retrofit> retrofitProvider) {
    return new ModuleApi_ProvideJournalFactory(module, retrofitProvider);
  }

  public static JournalApi proxyProvideJournal(ModuleApi instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideJournal(retrofit),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
