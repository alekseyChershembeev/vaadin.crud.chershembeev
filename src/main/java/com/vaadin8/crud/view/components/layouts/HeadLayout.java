package com.vaadin8.crud.view.components.layouts;

import com.vaadin8.crud.view.windows.AddCompanyWindow;
import com.vaadin8.crud.view.windows.AddEmployeeWindow;
import com.vaadin8.crud.view.windows.DeleteCompanyWindow;
import com.vaadin8.crud.view.windows.DeleteEmployeeWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.logging.Logger;

import static com.vaadin8.crud.view.components.layouts.MainLayout.*;

class HeadLayout extends HorizontalLayout {

    private static Logger logger = Logger.getLogger(HeadLayout.class.getName());

    static TextField searchField;
    private Button addButton;
    private Button deleteButton;

    HeadLayout() {
        setComponents();
        setStyle();

        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(addButton, deleteButton);

        addComponents(hl, searchField);
        setComponentAlignment(searchField, Alignment.MIDDLE_RIGHT);

        setClickListeners();

    }

    private void setStyle() {
        setMargin(false);
        setSpacing(true);
        setSizeFull();
    }

    private void setComponents() {
        addButton = new Button("Добавить");
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setIcon(VaadinIcons.INSERT);
        deleteButton = new Button("Удалить");
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setIcon(VaadinIcons.MINUS);
        searchField = new TextField();
        searchField.setPlaceholder("поиск");
    }


    private void setClickListeners() {
        /*Добавляем новое окошко взависимости от выбранной табы при нажатии кнопки Добавить*/
        addButton.addClickListener(clickEvent -> {

            if (tabSheet.getSelectedTab().equals(tabCompany)) {

                AddCompanyWindow addComWindow = new AddCompanyWindow();
                UI.getCurrent().addWindow(addComWindow);

                logger.info("Добавлено окно " + addComWindow.getStyleName());


            } else if (tabSheet.getSelectedTab().equals(tabEmployee)) {


                AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow();
                UI.getCurrent().addWindow(addEmployeeWindow);

                logger.info("Добавлено окно " + addEmployeeWindow.getStyleName());

            }
        });

         /*
         Добавляем новое окошко для удаления компании/сотрудника при нажатии кнопки Удалить
         */
        deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (tabSheet.getSelectedTab().equals(tabCompany)) {
                DeleteCompanyWindow deleteComWindow = new DeleteCompanyWindow();
                UI.getCurrent().addWindow(deleteComWindow);

                logger.info("Добавлено окно " + deleteComWindow.getStyleName());


            } else if (tabSheet.getSelectedTab().equals(tabEmployee)) {
                DeleteEmployeeWindow deleteWindow = new DeleteEmployeeWindow();
                UI.getCurrent().addWindow(deleteWindow);

                logger.info("Добавлено окно " + deleteWindow.getStyleName());

            }
        });
    }


}
