package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import controller.MemberService;
import controller.MemberServiceImpl;
import controller.TestController;
import controller.TestControllerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Member;

public class MemberViewController implements Initializable {
	@FXML	private Button btnRegister;
	@FXML	private Button btnUpdate;
	@FXML	private Button btnDelete;
	
	@FXML	private Button btnExecute;
	@FXML	private TextArea taExecute;
	@FXML	private TextField tfExecute;
	
	@FXML	private TextField tfID;
	@FXML	private PasswordField tfPW;
	@FXML	private TextField tfName;
	@FXML	private TextField tfMobilePhone;
	
	@FXML 	private TableView<Member> tableViewMember;
	@FXML	private TableColumn<Member, String> columnName;
	@FXML	private TableColumn<Member, String> columnID;
	@FXML	private TableColumn<Member, String> columnPW;
	@FXML	private TableColumn<Member, String> columnMobilePhone;
	
	
	private final ObservableList<Member> data = FXCollections.observableArrayList();
	ArrayList<Member> memberList;
	MemberService memberService;  
	
	TestController ts;
	
	public MemberViewController() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		memberService = new MemberServiceImpl();
		ts = new TestControllerImpl();
		/*columnName.setCellValueFactory(cvf -> cvf.getValue().unameProperty());
		columnID.setCellValueFactory(cvf -> cvf.getValue().uidProperty());
		//columnPW.setCellValueFactory(cvf -> cvf.getValue().upwProperty());
		
		tableViewMember.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showMemberInfo(newValue));
		
				
		// btnDelete.setOnMouseClicked(e -> handleDelete());*/
		btnExecute.setOnMouseClicked(event -> handleExecute());
		
		//loadMemberTableView();
		
	}
	String str = "";
	@FXML 
	private void handleExecute() { // event source, listener, handler
		str = taExecute.getText();
		str = ts.appendTextArea(name);
		taExecute.setText(str);
	}
	
	private void showMemberInfo(Member member) {
		if (member != null) {
			tfID.setText(member.getUid());
			tfPW.setText(member.getUpw());
			tfName.setText(member.getUname());
			tfMobilePhone.setText(member.getMobilePhone());
		}
		 else {
			 tfID.setText("");
			 tfPW.setText("");
		     tfName.setText("");
		     tfMobilePhone.setText("010");
		 }
	}
	
	private void loadMemberTableView() {
		memberList = memberService.readList();
		for(Member m : memberList) {
			data.add(m);
		}
		tableViewMember.setItems(data);
	}
	
	@FXML 
	private void handleCreate() { // event source, listener, handler
		if(tfID.getText().length() > 0) {
			Member newMember = new Member(tfID.getText(), tfPW.getText(), tfName.getText(), tfMobilePhone.getText());
			if(memberService.create(newMember) >= 0)	
				data.add(newMember);
			else
				showAlert("ID �ߺ����� ����� �� �����ϴ�.");
		} else
			showAlert("ID�� �ʼ��׸� �Դϴ�.");
	}
	@FXML 
	private void handleUpdate() {
		Member newMember = new Member(tfID.getText(), tfPW.getText(), tfName.getText(), tfMobilePhone.getText());

		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			tableViewMember.getItems().set(selectedIndex, newMember);
			memberService.update(newMember);			
		} else {
			showAlert("������ �� �� �����ϴ�.");          
        }
	}
	
	@FXML 
	private void handleDelete() {
		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			memberService.delete(tableViewMember.getItems().remove(selectedIndex));			
		} else {
			showAlert("������ �� �� �����ϴ�.");
        }
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainApp.getRootStage());
        alert.setTitle("Ȯ��");
        alert.setContentText("Ȯ�� : " + message);            
        alert.showAndWait();
	}

	private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
