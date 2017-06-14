/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author GabrielRS
 */
public class GerenciadorDeConexao implements Runnable{
    
    private ServerSocket serverSocket;
    private Socket socket;
//    private LinkedList<Thread> conexoes;
    private ArrayList<Thread> conexoes;
    private BooleanProperty novoSocket;
    
    public GerenciadorDeConexao() {
        this.startServer();
        this.conexoes = new ArrayList<Thread>(3);
        this.novoSocket = new SimpleBooleanProperty(false);
    }
    
    public void run(){
        while(true){
            if(serverSocket != null){
                System.out.println("Escutando - "+Thread.currentThread().getId());
                while(true){
                    try{
                        this.socket = serverSocket.accept();
                        if(socket != null)
                            this.novoSocket.set(true);
                        System.out.println("Conexão recebida de: "+socket.getLocalAddress().getHostAddress());
                        this.novoSocket.set(false);
//                        if(!this.threadList(con)){//Adiciona uma nova Conexão na LinkedList
//                            if(this.threadChecker()) //Caso não seja inserida é verificado o status de todas as Threads de conexão
//                                this.threadList(con); //Caso alguma Thread tenha sido removida é repetida a tentativa de inserção
//                        }
                    }catch(Exception e){
                        System.out.println("Falha na execução:\n"+ e.getMessage());
                    }
                }
            }else{
                this.startServer();
            }
        }
    }
    
    private void startServer(){
        try{
            this.serverSocket = new ServerSocket(3000);
        }
        catch (Exception e){
            this.serverSocket = null;
        }
    }
    
    public boolean threadList(Conexao novaConexao){
        System.out.println("tamanho "+conexoes.size());
        if(this.conexoes.size() < 3){
            novaConexao.setPosicao((this.conexoes.size()+1));
            System.out.println("A posição é: "+novaConexao.getPosicao());
            conexoes.add(new Thread(novaConexao));
            conexoes.get(conexoes.size()-1).start();
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean threadChecker(){
        ListIterator<Thread> cons = this.conexoes.listIterator();
        Thread conexao;
        boolean flag = false;
        while(cons.hasNext()){
            conexao = cons.next();
            if(conexao.isInterrupted()){
                this.conexoes.remove(conexao);
                flag = true;
            }
        }
        return flag;
    }

    public BooleanProperty getNovoSocket() {
        return novoSocket;
    }

    public Socket getSocket() {
        return socket;
    }
}
