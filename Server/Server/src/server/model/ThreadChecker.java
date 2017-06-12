/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author GabrielRS
 */
public class ThreadChecker implements Runnable {
    
    LinkedList conexoes;
    
    public ThreadChecker(LinkedList conexoes){
        this.conexoes = conexoes;
    }
    
    public void run(){
        ListIterator<Thread> cons = this.conexoes.listIterator();
        Thread conexao;
        while(cons.hasNext()){
            conexao = cons.next();
            if(conexao.isInterrupted())
                this.conexoes.remove(conexao);
        }
        Thread.currentThread().interrupt();
    }
    
}
