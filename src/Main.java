/* @author Neptune */
import java.util.Scanner;

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
