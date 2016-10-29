/* @author Neptune */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    int id = 0;
    String host = "127.0.0.1";
    String name = "";
    BufferedReader reader;
    PrintStream writer;
    Scanner sc;
    Socket sock;

    public Client(String name,String host) {
        this.name = name;
        this.host = host;
        this.EstablishConnection();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void giveServerInfo(String name){
        writer.println(name);
        writer.flush();
    }


    private void EstablishConnection() {
        try {
            sock = new Socket(host, 19999);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintStream(sock.getOutputStream());
            sc = new Scanner(System.in);
            System.out.println("網路建立-連線成功");

            setId(Integer.parseInt(reader.readLine()));
            giveServerInfo(name);

            Thread readerThread = new Thread(new IncomingReader());
            readerThread.start();
            Thread writerThread = new Thread(new OutputWriter());
            writerThread.start();

			//自動一直拿一直拿
            Thread AutoGet = new Thread(new MachineGet());
            AutoGet.start();


        } catch (IOException ex) {
            System.out.println("建立連線失敗");
        }
    }

    public class IncomingReader implements Runnable {

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class OutputWriter implements Runnable {

        public void run() {
            String message;
            try {
                while ((message = sc.nextLine()) != null) {
                    writer.println(message);
                    writer.flush();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MachineGet implements Runnable{
        String items[] = new String[]{"Abalone","Hot_Girl","Hot_Dog","Sexy_Fish","Shark_Wing","30CM_Dick"};
        public void run() {
            for(int i=0;;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
                writer.println("GET " +items[i%6]);
                writer.flush();
            }
        }
    }

}
