/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author deine
 */

public class UserAdmin {
    
    private Vector<User> users = new Vector();//vector para administar el login
    private String path = "users.txt";

    public UserAdmin() {
        //si es la primer vez que entra en la aplicacion esta es el usuario y contrasena default
        User admin = new User("admin","admin");
        users.add(admin);
    }
    
    public void getData(){
        users.clear();
        
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String x;
            
            while( (x = br.readLine()) != null){//br.readLine() retorn null cuando se termino el archivo
                StringTokenizer data = new StringTokenizer(x," ");
                while(data.hasMoreTokens()){
                    User user = new User(data.nextToken(),data.nextToken());
                    users.add(user);
                }
            }
        }
        catch(Exception e){
            
        }
    }//fin getData
    
    public void saveData(){
        try{
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            for(User user:users){
                bw.write(user.getName());
                 bw.write(" "+user.getPassword()+"\n");
            }
            
            pw.close();
        }
        catch(Exception e){
            
        }
    }//fin saveData
    
    public boolean find(String name){
        
        for(User user:users){
            if(user.getName().equals(name)){
                return true;
            }
        }
        
        return false;
    }//fin find
    
    public boolean validate(String name,String password){
        for(User user:users){
            if( user.getName().equals(name) && user.getPassword().equals(password) ){
                return true;
            }
        }
        return false;
    }//fin validate
    
    
}//fin clase
