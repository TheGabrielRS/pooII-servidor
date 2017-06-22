/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import server.model.Conexao;

import server.model.GerenciadorDeConexao;

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
    
    GerenciadorDeConexao objGerenciadorDeConexao;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // TODO
        this.startListen();
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
    public void fileWatch(String adicionados, String removidos){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UFCSPA - Programação Orientada a Objetos II");
        alert.setHeaderText("Alteração de Arquivos");
        alert.setContentText("Cliente: " + "id do cliente"
                + "\n" + "Arquivos Removidos: "  + removidos
                + "\n" + "Arquivos Adicionados: " + adicionados);

        alert.showAndWait();
    }
    
    public void startListen(){
        
        this.objGerenciadorDeConexao = new GerenciadorDeConexao();
        
        this.objGerenciadorDeConexao.getNovoSocket().addListener(new ChangeListener<Boolean>(){
            public void changed(final ObservableValue<? extends Boolean> observable,
          final Boolean oldValue, final Boolean newValue){
                if(newValue){
                Conexao novaConexao = new Conexao(objGerenciadorDeConexao.getSocket());
                
                
                
                novaConexao.getMsg().addListener(new ChangeListener<String>(){
                    public void changed(final ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue){
                        if(!newValue.equals("") && !newValue.equals("c53255317bb11707d0f614696b3ce6f221d0e2f2") && !newValue.matches("fileWatcher:\\d:\\d")){
                            ObservableList obsl;
                            switch(novaConexao.getPosicao()){
                                case 1:
                                    obsl = FXCollections.observableList(Arrays.asList(newValue.substring(1,newValue.length()-1).split(",")));
                                    Platform.runLater(()->{
                                        listOne.setItems(obsl);
                                    });
                                    break;
                                case 2:
                                    obsl = FXCollections.observableList(Arrays.asList(newValue.substring(1,newValue.length()-1).split(",")));
                                    Platform.runLater(()->{ // isso é um lambda
                                        listTwo.setItems(obsl);
                                    });
                                    break;
                                case 3:
                                    obsl = FXCollections.observableList(Arrays.asList(newValue.substring(1,newValue.length()-1).split(",")));
                                    Platform.runLater(()->{
                                       listThree.setItems(obsl);
                                    });
                                    break;
                            }
                            novaConexao.getMsg().set("");
                        }
                    }
                });
                
                novaConexao.getFileWatcher().addListener(new ChangeListener<String>(){
                    public void changed(final ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue){
                        System.out.println("File Watcher: "+newValue);
                        if(newValue.matches("fileWatcher:\\d:\\d")){
                            String fileChanges[] = newValue.split(":");
                            Platform.runLater(()->{fileWatch(fileChanges[1], fileChanges[2]);});
                            novaConexao.getFileWatcher().set("");
                        }
                    }
                });
                
                
                objGerenciadorDeConexao.threadList(novaConexao);
                switch(novaConexao.getPosicao()){
                        case 1:
                            Platform.runLater(()->{
                                ipOne.setText(novaConexao.getIp());
                            });
                            break;
                        case 2:
                            Platform.runLater(()->{
                                ipTwo.setText(novaConexao.getIp());
                            });
                            break;
                        case 3:
                            Platform.runLater(()->{
                                ipThree.setText(novaConexao.getIp());
                            });
                            break;
                    }
                }
            }
        });
        
        
        Thread gerenciadorDeConexao = new Thread(this.objGerenciadorDeConexao);
        gerenciadorDeConexao.setDaemon(true);
        gerenciadorDeConexao.start();
    }
}
