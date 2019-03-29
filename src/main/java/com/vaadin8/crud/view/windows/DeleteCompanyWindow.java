package com.vaadin8.crud.view.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin8.crud.dao.CompanyDaoImpl;
import com.vaadin8.crud.dao.dao.interfaces.CompanyDao;
import com.vaadin8.crud.entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;



public class DeleteCompanyWindow extends Window {

    private static Logger logger = Logger.getLogger(DeleteCompanyWindow.class.getName());
    private static CompanyDao companyDao;
    private List companyList;
    private ArrayList<Company> listCompany;
    private Set<Company> companySet;
    private Button deleteCompanyButton;
    private Button cancelButton;
    private CheckBoxGroup<Company> selectAllCompanies;

    public DeleteCompanyWindow() {
        setStyle();

        companyDao = new CompanyDaoImpl();

        companyList = companyDao.selectAllCompanies();

        setComponents();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(selectAllCompanies, deleteCompanyButton, cancelButton);

        setContent(verticalLayout);

        setClickListeners();

    }
    private void setStyle() {
        setStyleName("Удалить компанию");
        setWidth(270f, Unit.PIXELS);
        center();
        setClosable(true);
        setDraggable(false);
        setModal(true);
        setResizeLazy(false);
    }
    private void setComponents() {
        deleteCompanyButton = new Button("Удалить");
        deleteCompanyButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        deleteCompanyButton.setSizeFull();
        deleteCompanyButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteCompanyButton.setIcon(VaadinIcons.MINUS);

        cancelButton = new Button("Отменить", clickEvent -> close());
        cancelButton.setSizeFull();

        selectAllCompanies = new CheckBoxGroup<>("Выбрать компании");
        selectAllCompanies.setSizeFull();
        selectAllCompanies.setItems(companyList);
        selectAllCompanies.setItemCaptionGenerator(Company::getName);

    }

    private void setClickListeners() {

        selectAllCompanies.addValueChangeListener(valueChangeEvent -> {
            companySet = valueChangeEvent.getValue();
            listCompany = new ArrayList<>(companySet);

            logger.info("Выбраны компании " + companySet);
        });

        /*
         * Если в чекбоксе выбраны компании вызываем диалоговое окно
         */

        deleteCompanyButton.addClickListener(clickEvent -> {
            try {
                if (companySet.size()>0) {
                    UserConfirmationCompany userConfirmationCompany = new UserConfirmationCompany(companySet, listCompany);
                    UI.getCurrent().addWindow(userConfirmationCompany);
                    close();
                }
                else {
                    logger.warning("Не выбраны компании в чекбоксе ");
                }
            }catch (NullPointerException ex){
                logger.warning(" NPE deleteCompanyButton "+ ex);
            }
        });
    }
    }







