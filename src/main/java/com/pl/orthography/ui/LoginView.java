package com.pl.orthography.ui;

import com.pl.orthography.config.security.SecurityUtils;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;

@Route(value = LoginView.ROUTE)
@CssImport("styles/views/masterdetail/master-detail-view.css")
@JsModule("./styles/shared-styles.js")
public class LoginView extends Div implements BeforeEnterObserver, AfterNavigationObserver {

    public static final String ROUTE = "login";

    private LoginOverlay loginOverlay = new LoginOverlay();

    public LoginView() {
        System.out.println("Loooooooooooooooooogin");
        setId("login-view");
        createLoginOverlay();
        add(loginOverlay);
    }

    private void createLoginOverlay() {
        loginOverlay.setAction("login");
        loginOverlay.setOpened(true);
        loginOverlay.setTitle("Orthography App");
        loginOverlay.setDescription("Admin panel");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(UserManagementView.class);
        } else {
            loginOverlay.setOpened(true);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        loginOverlay.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}