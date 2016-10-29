/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Neptune
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    
    int count = 1;
    Item allItem;
    
    public static void main(String args[]) {
        new Server().establish();
        
    }
    
    public void establish() {
        allItem = new Item();
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        System.out.println(allItem.ItemStates());
                    } catch (InterruptedException ex) {
                        System.out.println("Item_States Repeat Break!!!");
                    }
                }
            }
        });
        tt.start();

        // 建立使用者連線管道
        try {
            ServerSocket serverSock = new ServerSocket(19999);
            while (true) {
                // 等待使用者連線
                Socket cSocket = serverSock.accept();
                Thread t = new Thread(new Process(count++, cSocket));
                t.start();
            }
        } catch (Exception ex) {
            System.out.println("Server Break!!!!");
        }
    }
    
    public class Process implements Runnable {
        
        String userName;
        PrintStream writer;
        BufferedReader reader;
        Socket sock;
        int id;
        
        public Process(int id, Socket cSocket) {
            try {
                this.sock = cSocket;
                this.id = id;
                this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                this.writer = new PrintStream(sock.getOutputStream());

                // Set Client id 
                writer.println(count);
                writer.flush();

                // Get Client info
                userName = reader.readLine().trim();

                // Start Connection
                Date now = new Date();
                String ip = cSocket.getInetAddress().getHostAddress();
                System.out.println(now.toString() + "\nIP : " + ip + "\nName : " + userName + "\nStart to Connect.\n");
                
                
            } catch (Exception ex) {
                System.out.println("失去連接");
            }
        }
        
        public void run() {
            String message;
            try {
                
                Thread tt = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(3000);
                                writer.println(allItem.ClientItemInfo(userName));
                                writer.flush();
                            } catch (InterruptedException ex) {
                                System.out.println("Item_States Repeat Break!!!");
                            }
                        }
                    }
                });
                tt.start();
                
                while ((message = reader.readLine()) != null) {
                    String ss[] = message.split(" ");
                    switch (ss[0]) {
                        case "RELEASE":
                            System.out.println(userName + " release " + ss[1]);
                            allItem.CallReleaseItem(ss[1], userName);
                            break;
                        case "GET":
                            System.out.println(userName + " try to get " + ss[1]);
                            if (allItem.hasItem(ss[1])) {
                                writer.println(allItem.getItem(ss[1], userName));
                                writer.flush();
                            } else {
                                writer.println("No Such Object.");
                                writer.flush();
                            }
                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("有一個連接離開");
            }
        }
        
    }
    
}
