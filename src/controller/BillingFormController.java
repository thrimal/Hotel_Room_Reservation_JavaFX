package controller;

import bo.BoFactory;
import bo.custom.ReservationBO;
import bo.custom.RoomBO;
import com.jfoenix.controls.JFXTextField;
import dao.DaoFactory;
import dao.custom.ReservationDAO;
import db.DBConnection;
import dto.ReservationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.ReservationTM;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BillingFormController implements Initializable {
    public AnchorPane BillingForm;
    public TableView<ReservationTM> tblReservation;
    public JFXTextField txtCustomerId;
    public JFXTextField txtReservationId;
    public JFXTextField txtRoomId;
    public JFXTextField txtPayment;
    public JFXTextField txtReservationDate;
    public JFXTextField txtInDate;
    public JFXTextField txtOutDate;
    public JFXTextField txtServiceId;
    public JFXTextField txtServiceTerms;
    public JFXTextField txtServiceAmount;
    private ReservationBO reservationBO= BoFactory.getInstance().getBo(BoFactory.BoType.RESERVATION);
    private RoomBO roomBO=BoFactory.getInstance().getBo(BoFactory.BoType.ROOM);

    public void setUI(String location) throws IOException {
        Stage stage= (Stage) BillingForm.getScene().getWindow();
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

    public void searchReservationOnAction(ActionEvent actionEvent)  {
        ReservationDTO selectedReservationDetail = null;
        try {
            selectedReservationDetail = reservationBO.getSelectedReservationDetail(txtReservationId.getText());
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Reservation Not Found").show();
        }
        if(selectedReservationDetail != null){
            txtCustomerId.setText(selectedReservationDetail.getCustomerId());
            txtServiceId.setText(selectedReservationDetail.getServiceId());
            txtRoomId.setText(selectedReservationDetail.getRoomId());
            txtReservationDate.setText(selectedReservationDetail.getReservationDate());
            txtInDate.setText(selectedReservationDetail.getCheckInDate());
            txtOutDate.setText(selectedReservationDetail.getCheckOutDate());
            txtServiceTerms.setText(selectedReservationDetail.getTerms()+"");
            txtServiceAmount.setText(selectedReservationDetail.getServiceAmount()+"");
            txtPayment.setText(selectedReservationDetail.getFullPayment()+"");
            System.out.println(selectedReservationDetail);
        }
    }


    public void btnCancelBookingOnAction(ActionEvent actionEvent) throws Exception {
        if(txtReservationId.getText().isEmpty()|txtPayment.getText().isEmpty()|txtCustomerId.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Fields Empty, Search Reservation First").show();
        }
        boolean cancelBooking = reservationBO.cancelBooking(txtReservationId.getText());
        if(cancelBooking){
            new Alert(Alert.AlertType.INFORMATION,"Booking Cancelation Success").show();
            loadAllReservation();
            roomBO.updateStatusAvailable(txtRoomId.getText());
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Booking Cancelation Faild").show();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        try {
            loadAllReservation();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadAllReservation() throws SQLException, ClassNotFoundException {
        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();
        ArrayList<ReservationDTO> reservationDTOS=reservationBO.getAllReservationDetailByDate();
        for (ReservationDTO d:reservationDTOS) {
            reservationTMS.add(new ReservationTM(
                    d.getReservationId(),
                    d.getCustomerId(),
                    d.getServiceId(),
                    d.getRoomId(),
                    d.getReservationDate(),
                    d.getCheckInDate(),
                    d.getCheckOutDate(),
                    d.getTerms(),
                    d.getServiceAmount(),
                    d.getFullPayment()
            ));
            tblReservation.setItems(reservationTMS);
        }
    }

    public void btnBillOnAction(ActionEvent actionEvent) {
        try {
            roomBO.updateStatusAvailable(txtRoomId.getText());
            String status="Non Available";
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            InputStream is=this.getClass().getResourceAsStream("/report/Hotel.jasper");
            //JasperReport jr= JasperCompileManager.compileReport(is);

            HashMap hm=new HashMap();

            hm.put("resId",txtReservationId.getText());
            hm.put("custId",txtCustomerId.getText());
            hm.put("roomId",txtRoomId.getText());
            hm.put("serviceId",txtServiceId.getText());
            hm.put("reservationDate",txtReservationDate.getText());
            hm.put("checkInDate",txtInDate.getText());
            hm.put("checkOutDate",txtOutDate.getText());
            hm.put("serviceTerms",Integer.parseInt(txtServiceTerms.getText()));
            hm.put("serviceAmount",Double.parseDouble(txtServiceAmount.getText()));
            hm.put("fullPayment",Double.parseDouble(txtPayment.getText()));

            JasperPrint jp= JasperFillManager.fillReport(is,hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jp,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
