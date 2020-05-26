package client.api;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = ModuleApi.class)
public interface ApiComponent {
    Auth provideAuthApi();
    JournalApi provideJournal();
    ClientApi provideClient();
    BookTypeApi provideType();
    BookApi provideBook();
}
