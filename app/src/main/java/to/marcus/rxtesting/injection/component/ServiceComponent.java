package to.marcus.rxtesting.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.rxtesting.injection.module.ServiceModule;
import to.marcus.rxtesting.service.WordNotificationService;
import to.marcus.rxtesting.ui.activity.DetailActivity;

/**
 * Created by mplienegger on 10/30/2015.
 */
@Component(dependencies = BaseAppComponent.class, modules = ServiceModule.class)

public interface ServiceComponent {
    void injectService(WordNotificationService service);
    Context context();
}
