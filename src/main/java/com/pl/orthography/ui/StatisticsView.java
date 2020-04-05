package com.pl.orthography.ui;

import com.pl.orthography.service.UserService;
import com.pl.orthography.ui.statisticslayout.AccountsStatistics;
import com.pl.orthography.ui.statisticslayout.StatisticType;
import com.pl.orthography.ui.statisticslayout.UserRegistrationsPerDay;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "statistics", layout = MainView.class)
@PageTitle("Statistics")
public class StatisticsView extends Div implements AfterNavigationObserver {

    VerticalLayout statisticTypeLayout;

    private UserService userService;

    @Autowired
    public StatisticsView(UserService userService) {
        this.userService = userService;

        createStatisticTypeComboBox();

        statisticTypeLayout = new VerticalLayout();
        statisticTypeLayout.add(new AccountsStatistics(userService));
        add(statisticTypeLayout);

        setWidth("100%");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    }

    private void createStatisticTypeComboBox() {
        ComboBox<StatisticType> comboBox = new ComboBox<>();
        comboBox.setItems(StatisticType.values());
        comboBox.setValue(StatisticType.ACCOUNTS_STATISTICS);
        comboBox.setWidth("100%");

        comboBox.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<StatisticType>, StatisticType>>) event -> {
            if (!event.getOldValue().equals(event.getValue())) {
                statisticTypeLayout.removeAll();
                statisticTypeLayout.add(createStatisticBasedOnType(event.getValue()));
            }
        });

        add(comboBox);
    }

    private Component createStatisticBasedOnType(StatisticType value) {
        switch (value) {
            case ACCOUNTS_STATISTICS:
                return new AccountsStatistics(userService);
            case USER_REGISTRATION_PER_DAY:
                return new UserRegistrationsPerDay(userService);
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}