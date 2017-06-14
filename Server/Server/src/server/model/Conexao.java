/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author gabrielsa
 */
public class Conexao implements Runnable{
    
    private Socket clientSocket;
    private InputStreamReader reader;
    private int posicao;
    private StringProperty msg;
    private ObservableList listaQueVem;

    public Conexao(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.msg = new SimpleStringProperty("");
        try{
            this.reader = new InputStreamReader(this.clientSocket.getInputStream());
        }catch(Exception e){
            System.out.println("Input stream não pode ser definido");
        }
    }
    
    public void run(){
        boolean flag = true;
        System.out.println("Conexão aberta "+ Thread.currentThread().getId());
        while(flag){
            try{
                
                BufferedReader bfReader = new BufferedReader(reader);
                msg.set(bfReader.readLine());
                if(msg.get() == null){
                    flag = false;
                }
                else if(!msg.get().equals("c53255317bb11707d0f614696b3ce6f221d0e2f2")){
                    this.converteLista();
                    System.out.println("Do cliente "+clientSocket.getLocalAddress().getHostAddress()+" : "+msg.get()+" Thread: "+Thread.currentThread().getId());
                }
                else{
                    System.out.println("vai morrer");
                    Thread.currentThread().interrupt();
                }
            }catch(IOException e){
                System.out.println("Conexão encerrada "+ Thread.currentThread().getId());
                flag = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public StringProperty getMsg() {
        return msg;
    }
    
    public ListView converteLista(){
        System.out.println("converteu");
        List<String> list = new ArrayList<String>();
        list.add(Integer.toString(posicao));
        ObservableList obsl = FXCollections.observableList(list);
        return new ListView(obsl);
    }
    
    
    
}