/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author heliof
 */
public class MainController {
    @FXML Label ipOne;
    @FXML Label ipTwo;
    @FXML Label ipThree;
    @FXML ListView listOne;
    @FXML ListView listTwo;
    @FXML ListView listThree;
    
    /*
    *   Variáveis com os estados de conexão 
    *   de cada cliente, porém, caso a 
    *   classe de conexão preveja isso,
    *   excluir essas variáveis.
    */
    String statusOne;
    String statusTwo;
    String statusThree; 
    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // TODO
        this.fileWatch();
    }    
    
    @FXML
    public void onInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UFCSPA - Programação Orientada a Objetos II");
        alert.setHeaderText("Aplicação servidor");
        alert.setContentText("Autores:" + 
                "\n" + "Gabriel Ramos dos Santos" +
                "\n" + "Hélio Francisco das Neves Silveira Jr.");

        alert.showAndWait();    
    }
    
    @FXML
    public void onDetail(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UFCSPA - Programação Orientada a Objetos II");
        alert.setHeaderText("Detalhes de Conexão");
        alert.setContentText("Cliente 1: " + statusOne +
                "\n" + "Cliente 2: " + statusTwo +
                "\n" + "Cliente 3: " + statusThree);

        alert.showAndWait();    
    }
    
    @FXML
    public void onTest(){
        this.reconnect();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UFCSPA - Programação Orientada a Objetos II");
        alert.setHeaderText("Testando de Conexão");
        alert.setContentText("Cliente 1: " + statusOne +
                "\n" + "Cliente 2: " + statusTwo +
                "\n" + "Cliente 3: " + statusThree);

        alert.showAndWait();    
    }
    
    public void reconnect(){
        /*
        *   Fazer uma função de reconexão
        */
        statusOne = "Conectando";        
        statusTwo = "Conectando";        
        statusThree = "Conectando";   
    }
    
    /*
    *   Caso haja algum acréscimo de arquivos de 
    *   imagens ou exclusão, a estação servidora
    *   deverá exibir uma janela de diálogo ao final
    *   da transferência de nomes de arquivos,
    *   informando quantos arquivos foram excluídos 
    *   e quantos foram adicionados.
    */
    public void fileWatch(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UFCSPA - Programação Orientada a Objetos II");
        alert.setHeaderText("Alteração de Arquivos");
        alert.setContentText("Cliente: " + "id do cliente"
                + "\n" + "Arquivos Removidos: "  + "0"
                + "\n" + "Arquivos Adicionados: " + "0");

        alert.showAndWait();
    }
}
