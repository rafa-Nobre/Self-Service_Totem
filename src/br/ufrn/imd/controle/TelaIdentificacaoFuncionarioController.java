package br.ufrn.imd.controle;

import java.io.IOException;

import br.ufrn.imd.dao.Banco;
import br.ufrn.imd.modelo.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaIdentificacaoFuncionarioController {
	
	private Stage funcionarioStage;
	
	@FXML
    private MenuItem menuItemFecharJanela;
	
	@FXML
    private Button botaoChecarFuncionario;

    @FXML
    private Button botaoCadastrarFuncionario;
    
    @FXML
    private TextField textFieldNomeDoFuncionario;
    
    @FXML
    private TextField textFieldNomeCadastroDoFuncionario;
    
    @FXML
    private TextField textFieldResultados;
    
    @FXML
    private PasswordField textFieldSenhaDoSistema;
    
    @FXML
    void checarExistenciaFuncionario(ActionEvent event) throws IOException{
    	Banco banco = Banco.getInstance();
		
		String input1 = textFieldNomeDoFuncionario.getText();
		String input2 = textFieldSenhaDoSistema.getText();
		
		if(banco.getBancoFuncionarios().buscarFuncionario(input1) && banco.getSenhaDoSistema().equals(input2)) {
    		entrarMenuFuncionario();
    	}else {
    		textFieldResultados.setText("Funcion�rio n�o encontrado e/ou entrada inv�lida!");
    	}
    }
    
    @FXML
    void cadastrarFuncionario(ActionEvent event) {
    	Banco banco = Banco.getInstance();
    	
    	String input = textFieldNomeCadastroDoFuncionario.getText();
    	String input2 = textFieldSenhaDoSistema.getText();
    	
    	if(banco.getSenhaDoSistema().equals(input2)) {
    		
	    	if(!input.equals("")) {
				
				int id = banco.getBancoFuncionarios().getContadorParaId();
				
				Funcionario employee = new Funcionario();
				employee.setId(id);
				employee.setNome(input);
				banco.getBancoFuncionarios().cadastrarFuncionario(employee);
				
				textFieldResultados.setText("Funcionario Cadastrado!");
	    	}else {
	    		textFieldResultados.setText("Por favor informe seu nome!");
	    	}
    	}else {
    		textFieldResultados.setText("Senha inv�lida!");
    	}
    }
    
    @FXML
    void fecharJanela(ActionEvent event) {
    	funcionarioStage.close();
    }
    
    void entrarMenuFuncionario() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaMenuFuncionarioController.class.getResource("/br/ufrn/imd/visao/TelaMenuFuncionario.fxml"));
    	AnchorPane page = (AnchorPane) loader.load();
    	
    	Stage menuFuncionarioStage = new Stage();
    	menuFuncionarioStage.setTitle("Menu do Funcion�rio");
    	menuFuncionarioStage.setResizable(false);
    	Scene scene = new Scene(page);
    	menuFuncionarioStage.setScene(scene);
    	
    	TelaMenuFuncionarioController controller = loader.getController();
    	controller.setMenuFuncionarioStage(menuFuncionarioStage);
    	controller.preencherPedidos();
    	menuFuncionarioStage.showAndWait();
    	funcionarioStage.close();
    }

	public void setFuncionarioStage(Stage funcionarioStage) {
		this.funcionarioStage = funcionarioStage;
	}
}