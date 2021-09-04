package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.ReservationBO;
import bo.custom.RoomBO;
import bo.custom.ServiceBO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.ServiceDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.ReservationTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ReservationFormController implements Initializable {
    public AnchorPane ReservationForm;
    public ComboBox cmbGender;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;
    public ComboBox cmbRoomList;
    public Label lblReservationId;
    public ComboBox cmbServiceList;
    public TableView<ReservationTM> tblReservation;
    public Label lblDate;
    public Label lblAmountPerDay;
    public Label lblFullPayment;
    public JFXTextField txtGender;
    public ComboBox cmbRoomType;
    public Label lblRoomType;
    public Label lblServiceAmount;
    public Label lblRoomCondition;
    public Label lblServiceId;
    public JFXTextField txtTerms;
    public Label lblTime;
    public Label lblCountOfDays;
    public JFXDatePicker pckrCheckInDate;
    public JFXDatePicker pckrCheckOutDate;
    public Button btnAddNewCustomer;
    public Button btnAddReservation;
    private String checkInDate;
    private String checkOutDate;
    private ReservationBO reservationBO;
    private CustomerBO customerBO;
    private ServiceBO serviceBO;
    private RoomBO roomBO;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reservationBO= BoFactory.getInstance().getBo(BoFactory.BoType.RESERVATION);
        customerBO=BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);
        serviceBO=BoFactory.getInstance().getBo(BoFactory.BoType.SERVICE);
        roomBO=BoFactory.getInstance().getBo(BoFactory.BoType.ROOM);


        genarateDate();
        genarateTime();

        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(gender);

        try {
            loadResId();
            loadCustomerId();
            ObservableList<String> serviceType = FXCollections.observableArrayList();
            ArrayList<ServiceDTO> allService = serviceBO.getAllService();
            for (ServiceDTO d:allService) {
                serviceType.add(
                        d.getServiceType()
                );
                cmbServiceList.setItems(serviceType);
            }
        } catch (Exception e) {
          //  e.printStackTrace();
        }

        ObservableList<String> condition = FXCollections.observableArrayList("A/C", "NonA/C");
        cmbRoomType.setItems(condition);

    }
    public void loadResId(){
        try {
            String id = reservationBO.getReservationId();
            lblReservationId.setText(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadCustomerId() throws Exception {
        String id=customerBO.getCustomerId();
        txtCustomerId.setText(id);
    }

    public void setUI(String location) throws IOException {
        Stage stage= (Stage) ReservationForm.getScene().getWindow();
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

    public void AllReservationClick(ActionEvent actionEvent) throws IOException {
        setUI("AllReservationsForm.fxml");
    }

    public void btnMainOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MainForm.fxml");
    }

    public void btnAddReservationOnAction(MouseEvent mouseEvent) throws Exception{
        if(((txtCustomerName.getText().isEmpty() || txtNIC.getText().isEmpty()) || (txtEmail.getText().isEmpty() || txtContact.getText().isEmpty()))){
            new Alert(Alert.AlertType.WARNING,"Customer Fields Empty").show();
            txtCustomerName.requestFocus();
            return;
        }
        if(txtTerms.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Fields Empty You Need to Select InDate & OutDate").show();
            pckrCheckInDate.requestFocus();
            return;
        }
        if(cmbRoomType.getSelectionModel().getSelectedIndex()==-1){
            new Alert(Alert.AlertType.WARNING,"You Need to Select RoomType").show();
            cmbRoomType.requestFocus();
            return;
        }
        if(cmbRoomList.getSelectionModel().getSelectedIndex()==-1){
            new Alert(Alert.AlertType.WARNING,"You Need to Select Room").show();
            cmbRoomList.requestFocus();
            return;
        }
        if(cmbServiceList.getSelectionModel().getSelectedIndex()==-1){
            new Alert(Alert.AlertType.WARNING,"You Need to Select Service").show();
            cmbServiceList.requestFocus();
            return;
        }
        if(pckrCheckOutDate.getTypeSelector().isEmpty() || pckrCheckInDate.getTypeSelector().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"You Need to Select Chekc In Date & Check Out Date").show();
            pckrCheckInDate.requestFocus();
            return;
        }
        lblFullPayment.setText(fullAmount()+"");

        tblReservation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
        tblReservation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        tblReservation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ServiceId"));
        tblReservation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        tblReservation.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("ReservationDate"));
        tblReservation.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CheckInDate"));
        tblReservation.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CheckOutDate"));
        tblReservation.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Terms"));
        tblReservation.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("ServiceAmount"));
        tblReservation.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("FullPayment"));

        loadReservation();
    }

    public void btnPlaceOrderOnAction(MouseEvent mouseEvent) throws Exception {
        if(lblFullPayment.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"You Need to Add Reservation First").show();
            btnAddReservation.requestFocus();
            return;
        }
        boolean isAdded=false;
        isAdded=reservationBO.placeOrder(new ReservationDTO(
                        lblReservationId.getText(),
                        txtCustomerId.getText(),
                        lblServiceId.getText(),
                        cmbRoomList.getValue().toString(),
                        lblDate.getText(),
                        pckrCheckInDate.getValue().toString(),
                        pckrCheckOutDate.getValue().toString(),
                        Integer.parseInt(txtTerms.getText()),
                        Double.parseDouble(lblServiceAmount.getText()),
                        Double.parseDouble(lblFullPayment.getText())
                ));
        if(isAdded){
            boolean updateStatus = roomBO.updateStatusUnavailable(cmbRoomList.getValue().toString());
            if(updateStatus){
                clearFields();
                loadAvailableRooms();
                loadResId();
                loadCustomerId();
            }
            new Alert(Alert.AlertType.INFORMATION,"Reservation Added Successfully").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Reservation Faild, Add Customer First").show();
        }
    }

    public void NICOnAction(ActionEvent actionEvent) {
        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(txtNIC.getText());
            if(customerDTO != null){
                txtCustomerId.setText(customerDTO.getCustomerId());
                txtCustomerName.setText(customerDTO.getCustomerName());
                txtAddress.setText(customerDTO.getAddress());
                txtContact.setText(customerDTO.getContact()+"");
                txtEmail.setText(customerDTO.getEmail());
                txtGender.setText(customerDTO.getGender());
            }else{
                new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
        }
    }

    public void cmbServiceListOnAction(ActionEvent actionEvent) throws Exception {
        try {
            ServiceDTO serviceDTO = serviceBO.getServiceByType(cmbServiceList.getValue().toString());
            if (serviceDTO != null) {
                lblServiceId.setText(serviceDTO.getServiceId());
                lblServiceAmount.setText(serviceDTO.getServiceAmount() + "");
            }
        }catch (NullPointerException nullPointerException){

        }
    }

    public void cmbRoomTypeOnAction(ActionEvent actionEvent)  {
        loadAvailableRooms();
    }

    public void loadAvailableRooms()  {
        ArrayList<RoomDTO> allRoomsByCondition = null;
        try {
            allRoomsByCondition = roomBO.getAllRoomsByCondition(cmbRoomType.getValue().toString());
            ObservableList<String> roomListByCondition = FXCollections.observableArrayList();
            for (RoomDTO d : allRoomsByCondition){
                roomListByCondition.add(d.getRoomId());
                cmbRoomList.setItems(roomListByCondition);
            }
        }catch (NullPointerException nullPointerException){

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void CheckInDateAction(ActionEvent actionEvent)  {
        checkInDate=pckrCheckInDate.getValue().toString();
    }

    public void CheckOutDateAction(ActionEvent actionEvent)  {
        checkOutDate=pckrCheckOutDate.getValue().toString();
        System.out.println(pckrCheckOutDate.getValue().toString());
        txtTerms.setText(getCountOfDate(checkOutDate, checkInDate) + "");
        lblCountOfDays.setText(getCountOfDate(checkOutDate,checkInDate)+"");
    }

    public void cmbGenderOnAction(ActionEvent actionEvent) {
        txtGender.setText(cmbGender.getValue().toString());
    }

    public void cmbRoomListOnAction(ActionEvent actionEvent) /*throws Exception*/ {
        RoomDTO roomDTO = null;
        try {
            roomDTO = roomBO.searchRoom(cmbRoomList.getValue().toString());
            if(roomDTO != null){
                lblRoomType.setText(roomDTO.getRoomType());
                lblRoomCondition.setText(roomDTO.getRoomCondition());
                lblAmountPerDay.setText(roomDTO.getRoomAmount()+"");
            }
        } catch (NullPointerException nullPointerException){

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long getCountOfDate(String checkOutDate,String checkInDate)  {
        Date out = null;
        Date in=null;
        long daydiff=0;
        try {
            out = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(checkOutDate);
            in = new SimpleDateFormat("yyyy-mm-dd",Locale.ENGLISH).parse(checkInDate);
            daydiff=Math.abs(out.getTime()-in.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            //e.printStackTrace();
        }catch (NullPointerException nullPointerException) {

        }
        return daydiff;
    }

    public double fullAmount()  {
        double serviceAmount=Double.parseDouble(lblServiceAmount.getText()) * Integer.parseInt(txtTerms.getText());
        double roomAmount=Double.parseDouble(lblAmountPerDay.getText()) * getCountOfDate(checkOutDate,checkInDate);
        double full=(double) serviceAmount + (double) roomAmount;
        return full;
    }

    public void genarateDate(){
        LocalDate d1=LocalDate.now();
        lblDate.setText(d1.toString());
    }

    public void genarateTime(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, event -> {
                DateTimeFormatter formatter= DateTimeFormatter.ofPattern("hh:mm:ss");
                lblTime.setText(LocalTime.now().format(formatter));}),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void clearFields(){
        txtCustomerName.clear();
        txtNIC.clear();
        txtTerms.clear();
        txtGender.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        cmbRoomList.getSelectionModel().clearSelection();
        cmbRoomType.getSelectionModel().clearSelection();
        cmbServiceList.getSelectionModel().clearSelection();
        lblFullPayment.setText("");
        lblCountOfDays.setText("Count of Days");
        lblAmountPerDay.setText("Per Day Amount");
        lblRoomCondition.setText("Room Condition");
        lblRoomType.setText("Room Type");
        lblServiceAmount.setText("Service Amount");
        lblServiceId.setText("Service Id");
    }

    public void btnAddNewCustomerOnAction(MouseEvent mouseEvent) throws Exception {
        if(Pattern.compile("^[0-9]{9}(v|V)$").matcher(txtNIC.getText()).matches()) {
            if(Pattern.compile("^([A-z]{1,}\\s[A-z]{1,})|([A-z]{1,})$").matcher(txtCustomerName.getText()).matches()) {
                if(Pattern.compile("^[0-9]{9,10}$").matcher(txtContact.getText()).matches()) {
                    if(Pattern.compile("^(.+)@(.+)$").matcher(txtEmail.getText()).matches()) {
                        boolean isAdded = reservationBO.addNewCustomer(new CustomerDTO(
                                txtCustomerId.getText(),
                                txtCustomerName.getText(),
                                txtAddress.getText(),
                                Integer.parseInt(txtContact.getText()),
                                txtEmail.getText(),
                                txtGender.getText(),
                                txtNIC.getText()
                        ));
                        if (isAdded) {
                            new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Customer Added Fail").show();
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

    public void loadReservation() {
        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();
        reservationTMS.add(new ReservationTM(
                lblReservationId.getText(),
                txtCustomerId.getText(),
                cmbServiceList.getValue().toString(),
                cmbRoomList.getValue().toString(),
                lblDate.getText(),
                pckrCheckInDate.getValue().toString(),
                pckrCheckOutDate.getValue().toString(),
                Integer.parseInt(txtTerms.getText()),
                Double.parseDouble(lblServiceAmount.getText()),
                Double.parseDouble(lblFullPayment.getText())
        ));
        tblReservation.setItems(reservationTMS);
    }
}
