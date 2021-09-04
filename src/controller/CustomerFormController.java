package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerFormController implements Initializable {
    public AnchorPane CustomerForm;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXTextField txtGender;
    public JFXTextField txtNIC;
    public ComboBox<String> cmbGender;
    public TableView<CustomerTM> tblCustomer;
    private CustomerBO customerBO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerBO=BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);

        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(gender);


        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblCustomer.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("NIC"));

        loadAllCustomer();
        txtNIC.requestFocus();
    }


    public void setUI(String location) throws IOException {
        Stage stage= (Stage) CustomerForm.getScene().getWindow();
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

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(((txtCustomerName.getText().isEmpty() || txtNIC.getText().isEmpty()) || (txtEmail.getText().isEmpty() || txtContact.getText().isEmpty()))){
            new Alert(Alert.AlertType.WARNING,"Customer Fields Empty").show();
            txtCustomerName.requestFocus();
            return;
        }
                                    try {
                                        boolean isDeleted = customerBO.deleteCustomer(txtCustomerId.getText());
                                        if(isDeleted){
                                            new Alert(Alert.AlertType.INFORMATION,"Delete Successfull").show();
                                            clearFields();
                                            loadAllCustomer();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
    }

    public void SearchOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[0-9]{9}(v|V)$").matcher(txtNIC.getText()).matches()) {
            try {
                CustomerDTO customerDTO = customerBO.searchCustomer(txtNIC.getText());
                if (customerDTO != null) {
                    txtCustomerId.setText(customerDTO.getCustomerId());
                    txtCustomerName.setText(customerDTO.getCustomerName());
                    txtAddress.setText(customerDTO.getAddress());
                    txtContact.setText(customerDTO.getContact() + "");
                    txtEmail.setText(customerDTO.getEmail());
                    txtGender.setText(customerDTO.getGender());
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Customer Not Found").show();
            }
        }else {
            txtNIC.setFocusColor(Paint.valueOf("red"));
            txtNIC.requestFocus();
        }
    }


    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(Pattern.compile("^[0-9]{9}(v|V)$").matcher(txtNIC.getText()).matches()) {
            if(Pattern.compile("^([A-z]{1,}\\s[A-z]{1,})|([A-z]{1,})$").matcher(txtCustomerName.getText()).matches()) {
                if(Pattern.compile("^[0-9]{9,10}$").matcher(txtContact.getText()).matches()) {
                    if(Pattern.compile("^(.+)@(.+)$").matcher(txtEmail.getText()).matches()) {
                        try {
                            boolean isUpdate = customerBO.updateCustomer(new CustomerDTO(
                                    txtCustomerId.getText(),
                                    txtCustomerName.getText(),
                                    txtAddress.getText(),
                                    Integer.parseInt(txtContact.getText()),
                                    txtEmail.getText(),
                                    txtGender.getText(),
                                    txtNIC.getText()
                            ));

                            if (isUpdate) {
                                new Alert(Alert.AlertType.INFORMATION, "Update Successfull").show();
                                clearFields();
                                loadAllCustomer();
                            }
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.WARNING, "Update Fail").show();
                        }
                    }else{
                        txtEmail.setFocusColor(Paint.valueOf("red"));
                        txtEmail.requestFocus();
                    }
                }else{
                    txtContact.setFocusColor(Paint.valueOf("red"));
                    txtContact.requestFocus();
                }
            }else {
                txtCustomerName.setFocusColor(Paint.valueOf("red"));
                txtCustomerName.requestFocus();
            }
        }else {
            txtNIC.setFocusColor(Paint.valueOf("red"));
            txtNIC.requestFocus();
        }
    }

    public void cmbGenderOnAction(ActionEvent actionEvent) {
        txtGender.setText(cmbGender.getValue());
    }

    public void loadAllCustomer(){
        try {
            ObservableList<CustomerTM> customers = FXCollections.observableArrayList();
            ArrayList<CustomerDTO> allCustomer = customerBO.getAllCustomer();
            for (CustomerDTO d: allCustomer) {
                customers.add(new CustomerTM(
                        d.getCustomerId(),
                        d.getCustomerName(),
                        d.getAddress(),
                        d.getContact(),
                        d.getEmail(),
                        d.getGender(),
                        d.getNIC()
                ));
            }
            tblCustomer.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(){
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtContact.clear();
        cmbGender.getSelectionModel().clearSelection();
        txtGender.clear();
        txtAddress.clear();
        txtEmail.clear();
    }
}
