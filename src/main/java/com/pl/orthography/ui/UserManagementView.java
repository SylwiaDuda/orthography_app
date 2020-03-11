package com.pl.orthography.ui;

import com.pl.orthography.data.entity.AccountState;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.service.UserService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "user-management", layout = MainView.class)
@PageTitle("User-Management")
@CssImport("styles/views/masterdetail/master-detail-view.css")
public class UserManagementView extends Div implements AfterNavigationObserver {

    @Autowired
    private UserService userService;

    private Grid<User> userGrid;

    public UserManagementView() {
        System.out.println("Usssssssssssser management");
        setId("master-detail-view");

        createUserGrid();
        add(userGrid);
    }

    private void createUserGrid() {
        userGrid = new Grid<>();
        userGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        userGrid.setHeightFull();

        createUserGridColumn();
    }

    private void createUserGridColumn() {
        userGrid.addColumn(User::getEmail).setHeader("Email");
        userGrid.addColumn(User::getUserName).setHeader("User name");
        userGrid.addColumn(User::getRole).setHeader("Role");
        userGrid.addComponentColumn(this::createAccountStateComboBox).setHeader("Account state");
    }

    private ComboBox<AccountState> createAccountStateComboBox(User user) {
        ComboBox<AccountState> stateComboBox = new ComboBox<>();
        stateComboBox.setItems(AccountState.values());
        stateComboBox.setValue(user.getAccountState());

        stateComboBox.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<AccountState>, AccountState>>) event -> {
            if (!event.getOldValue().equals(event.getValue())) {
                userService.updateAccountState(user.getEmail(), event.getValue());
            }
        });
        return stateComboBox;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        userGrid.setItems(userService.getUsers());
    }
}