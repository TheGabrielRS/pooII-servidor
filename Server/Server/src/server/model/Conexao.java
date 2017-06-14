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

/**
 *
 * @author gabrielsa
 */
public class Conexao implements Runnable{
    
    private Socket clientSocket;
    private InputStreamReader reader;

    public Conexao(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
                String str = bfReader.readLine();
                System.out.println("Do cliente "+clientSocket.getLocalAddress().getHostAddress()+" : "+str+" Thread: "+Thread.currentThread().getId());
            }catch(IOException e){
                System.out.println("Conexão encerrada "+ Thread.currentThread().getId());
                flag = false;
                Thread.currentThread().interrupt();
            }
        }
    }
}