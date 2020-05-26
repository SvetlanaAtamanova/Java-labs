package client.api;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ModuleApi_ProvideGsonFactory implements Factory<Gson> {
  private final ModuleApi module;

  public ModuleApi_ProvideGsonFactory(ModuleApi module) {
    this.module = module;
  }

  @Override
  public Gson get() {
    return provideInstance(module);
  }

  public static Gson provideInstance(ModuleApi module) {
    return proxyProvideGson(module);
  }

  public static ModuleApi_ProvideGsonFactory create(ModuleApi module) {
    return new ModuleApi_ProvideGsonFactory(module);
  }

  public static Gson proxyProvideGson(ModuleApi instance) {
    return Preconditions.checkNotNull(
        instance.provideGson(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
