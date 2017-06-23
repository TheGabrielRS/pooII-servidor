/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    private IntegerProperty removido;
    private HashMap<Integer,Boolean> posicoesDisponiveis;
    
    
    public GerenciadorDeConexao() {
        this.startServer();
        this.conexoes = new ArrayList<Thread>(3);
        this.novoSocket = new SimpleBooleanProperty(false);
        this.removido = new SimpleIntegerProperty(0);
        this.iniciaPosicoesDisponiveis();
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
                        System.out.println("Conexão recebida de: "+socket.getInetAddress().getHostAddress());
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
        this.conexoes.trimToSize();
        System.out.println("tamanho "+conexoes.size());
        if(this.conexoes.size() < 3){
            novaConexao.setPosicao(this.ocupaPosicao());
            System.out.println("A posição é: "+novaConexao.getPosicao());
            conexoes.add(new Thread(novaConexao));
            conexoes.get(conexoes.size()-1).start();
            return true;
        }
        else{
            return false;
        }
    }
    
    public void threadChecker(){
        System.out.println("veio aqui");
        for(int p = 0; p < this.conexoes.size(); p++){
            System.out.println("percorreu");
            if(!this.conexoes.get(p).isAlive() || this.conexoes.get(p).isInterrupted()){
                if(this.conexoes.remove(p) != null)
                    System.out.println("removeu");
                this.conexoes.trimToSize();
            }
        }
//        for(Thread t : this.conexoes){
//            if(t.isInterrupted()){
//                this.conexoes.remove(t);
//            }
//        }
    }

    public BooleanProperty getNovoSocket() {
        return novoSocket;
    }

    public Socket getSocket() {
        return socket;
    }
    
    private void iniciaPosicoesDisponiveis(){
        this.posicoesDisponiveis = new HashMap();
        posicoesDisponiveis.put(1, true);
        posicoesDisponiveis.put(2, true);
        posicoesDisponiveis.put(3, true);
    };
    
    private int ocupaPosicao(){
        final int[] posicaoDisponivel = {0};
        this.posicoesDisponiveis.forEach((key, value)->{
            {
                if(posicaoDisponivel[0] == 0)
                    if(value)
                        posicaoDisponivel[0] = key;
            }
        });
        this.posicoesDisponiveis.replace(posicaoDisponivel[0], this.posicoesDisponiveis.get(posicaoDisponivel[0]) == true ? false : true);
        return posicaoDisponivel[0];
    }
    
    public void liberaPosicao(int posicao){
        this.posicoesDisponiveis.replace(posicao, true);
    }
}
