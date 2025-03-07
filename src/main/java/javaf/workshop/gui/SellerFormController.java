package javaf.workshop.gui;

import javaf.workshop.db.DbException;
import javaf.workshop.gui.listeners.DataChangerListener;
import javaf.workshop.gui.util.Alerts;
import javaf.workshop.gui.util.Constraints;
import javaf.workshop.gui.util.Utils;
import javaf.workshop.model.entities.Department;
import javaf.workshop.model.entities.Seller;
import javaf.workshop.model.exceptions.ValidationsException;
import javaf.workshop.model.service.DepartmentService;
import javaf.workshop.model.service.SellerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangerListener> dataChangerListeners = new ArrayList<>();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private ObservableList<Department> obsList;

    public void setSeller(Seller entity){
        this.entity = entity;
    }

    public void setSellerServices(SellerService service, DepartmentService departmentService){
        this.service = service;
        this.departmentService = departmentService;
    }

    public void subscribeDataChangeListener(DataChangerListener listener){
        dataChangerListeners.add(listener);
    }

    @FXML
    public void onBtSavenAction(ActionEvent event){
        if (entity == null){
            throw new IllegalStateException("entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("entity was null");
        }
        try{
        entity = getFormData();
        service.saveOrUpdate(entity);
        notifyDataChangeListeners();
        Utils.currentStage(event).close();
        }
        catch (DbException e){
            Alerts.showAlert("Error saving objects", null, e.getMessage(), Alert.AlertType.ERROR);
        }
        catch (ValidationsException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangerListener listener : dataChangerListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationsException exception = new ValidationsException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addErrors("name" , "Field can't be empty");
        }
        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addErrors("email" , "Field can't be empty");
        }

        obj.setName(txtName.getText());
        obj.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null){
            exception.addErrors("birthDate" , "Field can't be empty");

        }
        else{
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")){
            exception.addErrors("baseSalary" , "Field can't be empty");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if (exception.getErrors().size() > 0){
            throw exception;
        }
        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
        initializeComboBoxDepartment();
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f" ,entity.getBaseSalary()));
        if (entity.getBirthDate() != null){
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if (entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }
        else{

        comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects(){
        if (departmentService == null){
            throw new IllegalStateException("Department service was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        labelErrorName.setText(fields.contains("name") ? errors.get("name") : "");
        labelErrorEmail.setText(fields.contains("email") ? errors.get("email") : "");
        labelErrorBaseSalary.setText(fields.contains("baseSalary") ? errors.get("baseSalary") : "");
        labelErrorBirthDate.setText(fields.contains("birthDate") ? errors.get("birthDate") : "");
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
