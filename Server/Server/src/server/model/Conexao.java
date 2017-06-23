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
import java.util.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private StringProperty fileWatcher;
    private BooleanProperty removido;
    
    public Conexao(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.msg = new SimpleStringProperty("");
        this.fileWatcher = new SimpleStringProperty("");
        this.removido = new SimpleBooleanProperty(false);
        try{
            this.reader = new InputStreamReader(this.clientSocket.getInputStream());
        }catch(Exception e){
            System.out.println("Input stream não pode ser definido");
        }
    }
    
    public void run(){
//        boolean flag = true;
        System.out.println("Conexão aberta "+ Thread.currentThread().getId());
        while(!this.removido.get()){
            try{ 
                BufferedReader bfReader = new BufferedReader(reader);
                msg.set(bfReader.readLine());
//                String bfStr = bfReader.readLine();
//                System.out.println("Buffer Str: "+bfStr);
                if(msg.get() == null){
                    this.removido.set(true);
                }
                else if(msg.get().split(":")[0].equals("fileWatcher")){
                    System.out.println("entrou");
                    this.fileWatcher.set(msg.get());
                }
                else if(!msg.get().equals("c53255317bb11707d0f614696b3ce6f221d0e2f2")){
                    System.out.println("Do cliente "+clientSocket.getInetAddress().getHostAddress()+": "+msg.get()+" Thread: "+Thread.currentThread().getId());
                }
                else{
                    System.out.println("vai morrer");
                    Thread.currentThread().interrupt();
                }
            }catch(IOException e){
                System.out.println("Conexão encerrada "+ Thread.currentThread().getId());
                this.removido.set(true);
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
    
    public String getIp(){
        return clientSocket.getInetAddress().getHostAddress();
    }

    public StringProperty getFileWatcher() {
        return fileWatcher;
    }

    public BooleanProperty getRemovido() {
        return removido;
    }

    public long getThreadId() {
        return Thread.currentThread().getId();
    }
    
    
    
    
}