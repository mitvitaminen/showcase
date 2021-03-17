package net.croware.showcase.app.security;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.ui.views.FrontView;
import net.croware.showcase.ui.views.LoginView;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    private void beforeEnter(BeforeEnterEvent event) {
        final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
        if (!accessGranted) {
            if (SecurityUtils.isUserLoggedIn()) {
                event.rerouteTo(FrontView.class);
            } else {
                event.rerouteTo(LoginView.class);
            }
        }
    }

}
