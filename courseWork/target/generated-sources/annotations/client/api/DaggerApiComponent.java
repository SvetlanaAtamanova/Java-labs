package client.api;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerApiComponent implements ApiComponent {
  private ModuleApi_ProvideGsonFactory provideGsonProvider;

  private ModuleApi_ProvideRetrofitFactory provideRetrofitProvider;

  private Provider<Auth> provideAuthProvider;

  private Provider<JournalApi> provideJournalProvider;

  private Provider<ClientApi> provideClientProvider;

  private Provider<BookTypeApi> provideTypeProvider;

  private Provider<BookApi> provideBookProvider;

  private DaggerApiComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ApiComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideGsonProvider = ModuleApi_ProvideGsonFactory.create(builder.moduleApi);
    this.provideRetrofitProvider =
        ModuleApi_ProvideRetrofitFactory.create(builder.moduleApi, provideGsonProvider);
    this.provideAuthProvider =
        DoubleCheck.provider(
            ModuleApi_ProvideAuthFactory.create(builder.moduleApi, provideRetrofitProvider));
    this.provideJournalProvider =
        DoubleCheck.provider(
            ModuleApi_ProvideJournalFactory.create(builder.moduleApi, provideRetrofitProvider));
    this.provideClientProvider =
        DoubleCheck.provider(
            ModuleApi_ProvideClientFactory.create(builder.moduleApi, provideRetrofitProvider));
    this.provideTypeProvider =
        DoubleCheck.provider(
            ModuleApi_ProvideTypeFactory.create(builder.moduleApi, provideRetrofitProvider));
    this.provideBookProvider =
        DoubleCheck.provider(
            ModuleApi_ProvideBookFactory.create(builder.moduleApi, provideRetrofitProvider));
  }

  @Override
  public Auth provideAuthApi() {
    return provideAuthProvider.get();
  }

  @Override
  public JournalApi provideJournal() {
    return provideJournalProvider.get();
  }

  @Override
  public ClientApi provideClient() {
    return provideClientProvider.get();
  }

  @Override
  public BookTypeApi provideType() {
    return provideTypeProvider.get();
  }

  @Override
  public BookApi provideBook() {
    return provideBookProvider.get();
  }

  public static final class Builder {
    private ModuleApi moduleApi;

    private Builder() {}

    public ApiComponent build() {
      if (moduleApi == null) {
        this.moduleApi = new ModuleApi();
      }
      return new DaggerApiComponent(this);
    }

    public Builder moduleApi(ModuleApi moduleApi) {
      this.moduleApi = Preconditions.checkNotNull(moduleApi);
      return this;
    }
  }
}
