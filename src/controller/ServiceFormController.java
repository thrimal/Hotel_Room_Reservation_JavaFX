package controller;

import bo.BoFactory;
import bo.custom.ServiceBO;
import com.jfoenix.controls.JFXTextField;
import dao.DaoFactory;
import dto.CustomerDTO;
import dto.ServiceDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.tm.ServiceTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ServiceFormController implements Initializable {

    public AnchorPane ServiceForm;
    public JFXTextField txtServiceId;
    public JFXTextField txtServiceType;
    public JFXTextField txtServiceAmount;
    public TableView<ServiceTM> tblService;
    public TableColumn colServiceId;
    public TableColumn colServiceType;
    public TableColumn colServiceAmount;

    private ServiceBO serviceBO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceBO= BoFactory.getInstance().getBo(BoFactory.BoType.SERVICE);

        try {
            loadServiceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tblService.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        tblService.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        tblService.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("serviceAmount"));

        loadAllService();
    }

    public void loadServiceId() throws Exception {
        String id=serviceBO.getServiceId();
        txtServiceId.setText(id);
    }

    public void setUI(String location) throws IOException {
        Stage stage= (Stage) ServiceForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/"+location))));
    }

    public void CustomerFormClick(ActionEvent actionEvent) throws IOException {
        setUI("CustomerForm.fxml");
    }

    public void RoomFormClick(ActionEvent actionEvent) throws IOException {
        setUI("RoomForm.fxml");
    }

    public void ServiceFormClick(ActionEvent actionEvent) throws IOException {
        setUI("ServiceForm.fxml");
    }

    public void ReservationFormClick(ActionEvent actionEvent) throws IOException {
        setUI("ReservationForm.fxml");
    }

    public void SearchFormClick(ActionEvent actionEvent) throws IOException {
        setUI("BillingForm.fxml");
    }

    public void btnMainOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MainForm.fxml");
    }

    public void AllReservationClick(ActionEvent actionEvent) throws IOException {
        setUI("AllReservationsForm.fxml");
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(txtServiceType.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Fields Empty").show();
            txtServiceType.requestFocus();
            return;
        }
        if(Pattern.compile("^(S)[0-9]{1,}$").matcher(txtServiceId.getText()).matches()) {
            if (Pattern.compile("^[0-9].{1,}$").matcher(txtServiceAmount.getText()).matches()){
                try {
                    boolean isSaved = serviceBO.saveService(new ServiceDTO(
                            txtServiceId.getText(),
                            txtServiceType.getText(),
                            Double.parseDouble(txtServiceAmount.getText())
                    ));

                    if (isSaved) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Service is Added");
                        alert.show();
                        clearFields();
                        loadAllService();
                        loadServiceId();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Try Again");
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                txtServiceAmount.setFocusColor(Paint.valueOf("red"));
                txtServiceAmount.requestFocus();
            }
        }else{
            txtServiceId.setFocusColor(Paint.valueOf("red"));
            txtServiceId.requestFocus();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(S)[0-9]{1,}$").matcher(txtServiceId.getText()).matches()) {
            try {
                ServiceDTO serviceDTO = serviceBO.searchService(txtServiceId.getText());
                if(serviceDTO != null){
                    txtServiceType.setText(serviceDTO.getServiceType());
                    txtServiceAmount.setText(serviceDTO.getServiceAmount()+"");
                }else{
                    Alert alert=new Alert(Alert.AlertType.WARNING,"Service Not Found");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            txtServiceId.setFocusColor(Paint.valueOf("red"));
            txtServiceId.requestFocus();
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(txtServiceType.getText().isEmpty() | txtServiceAmount.getText().isEmpty() | txtServiceId.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Fields Empty").show();
            txtServiceType.requestFocus();
            return;
        }
        try {
            boolean isDeleted = serviceBO.deleteService(txtServiceId.getText());
            if(isDeleted){
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Service is Deleted");
                alert.show();
                clearFields();
                loadAllService();
                loadServiceId();
            }else{
                Alert alert=new Alert(Alert.AlertType.WARNING,"Delete Fail");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(txtServiceType.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Fields Empty").show();
            txtServiceType.requestFocus();
            return;
        }
        if(Pattern.compile("^(S)[0-9]{1,}$").matcher(txtServiceId.getText()).matches()) {
            if (Pattern.compile("^[0-9].{1,}$").matcher(txtServiceAmount.getText()).matches()){
                try {
                    boolean isUpdated = serviceBO.updateService(new ServiceDTO(
                            txtServiceId.getText(),
                            txtServiceType.getText(),
                            Double.parseDouble(txtServiceAmount.getText())
                    ));
                    if(isUpdated){
                        new Alert(Alert.AlertType.CONFIRMATION,"Update Successfully", ButtonType.YES,ButtonType.NO).showAndWait();
                        clearFields();
                        loadAllService();
                        loadServiceId();
                    }else{
                        Alert alert=new Alert(Alert.AlertType.WARNING,"Update Fail");
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                txtServiceAmount.setFocusColor(Paint.valueOf("red"));
                txtServiceAmount.requestFocus();
            }
        }else{
            txtServiceId.setFocusColor(Paint.valueOf("red"));
            txtServiceId.requestFocus();
        }
    }
    
    public void loadAllService(){
        try {
            ObservableList<ServiceTM> serviceList = FXCollections.observableArrayList();
            ArrayList<ServiceDTO> allService = serviceBO.getAllService();
            for (ServiceDTO dto : allService) {
                serviceList.add(new ServiceTM(
                        dto.getServiceId(),
                        dto.getServiceType(),
                        dto.getServiceAmount()
                ));
            }
            tblService.setItems(serviceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(){
        txtServiceType.clear();
        txtServiceAmount.clear();
    }
}
