/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.net.ServerSocket;

/**
 *
 * @author gabrielsa
 */
public class Conexao {
    
    private ServerSocket server;

    public Conexao() {
    }
    
    public boolean iniciaConexao(){
        try{
            this.server = new ServerSocket(3000);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
