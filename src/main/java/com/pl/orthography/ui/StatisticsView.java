package com.pl.orthography.ui;

import com.pl.orthography.service.UserService;
import com.pl.orthography.ui.statisticslayout.RegistrationUserPerDay;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "statistics", layout = MainView.class)
@PageTitle("Statistics")
public class StatisticsView extends Div implements AfterNavigationObserver {

    private UserService userService;

    @Autowired
    public StatisticsView(UserService userService) {
        this.userService = userService;

        add(new RegistrationUserPerDay(userService));

        setWidth("100%");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    }
}