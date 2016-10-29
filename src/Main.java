/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Fuck.*;
import java.util.Scanner;

/**
 *
 * @author Neptune
 */
public class Main {
    public static void main(String[] args){
       String name,ip;
       Scanner sc = new Scanner(System.in);
       System.out.print("What's Your Name : ");
       name = sc.nextLine();
       System.out.print("Server's IP : ");
       ip = sc.nextLine().trim();
       Client cc = new Client(name,ip);
    }
}
