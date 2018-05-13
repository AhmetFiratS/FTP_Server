/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpsrvrserver;

import FtpMessage.Message;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ahmet
 */
class ServerThread extends Thread {

    public void run() {
        //server kapanana kadar dinle
        while (!Server.serverSocket.isClosed()) {
            try {
                Server.Display("Client Bekleniyor...");
                // clienti bekleyen satır
                //bir client gelene kadar bekler
                Socket clientSocket = Server.serverSocket.accept();
                //client gelirse bu satıra geçer
                Server.Display("Client Geldi...");
                //gelen client soketinden bir sclient nesnesi oluştur
                //bir adet id de kendimiz verdik
                SClient nclient = new SClient(clientSocket, Server.IdClient);

                /* Message msg1 = new Message(Message.Message_Type.UserNumber);
                msg1.content=Server.IdClient;
                Server.Send(nclient,msg1);*/
                Server.IdClient++;
                //clienti listeye ekle.
                Server.Clients.add(nclient);
                //client mesaj dinlemesini başlat
                nclient.listenThread.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class Info {

    String username;
    String password;

    public Info(String u, String p) {
        this.username = u;
        this.password = p;
    }
}

public class Server {

    //server soketi eklemeliyiz
    public static ServerSocket serverSocket;
    public static int IdClient = 1;
    // Serverın dileyeceği port
    public static int port = 0;
    //Serverı sürekli dinlemede tutacak thread nesnesi
    public static ServerThread runThread;

    public static ArrayList<SClient> Clients = new ArrayList<>();
    public static ArrayList<Info> Infos = new ArrayList<>();

    //semafor nesnesi
    //public static Semaphore pairTwo = new Semaphore(1, true);
    // başlaşmak için sadece port numarası veriyoruz
    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static File downloadfile(String username,String filename){
        File root = new File("C:\\"+username);
        File[] listFiles = root.listFiles();
        for (File file : listFiles) {
            if (file.getName().equals(filename)) {
                return file;
            }
        }
        return null;
    }
    
    public static boolean DeleteFile(String username,String filename){
        File root = new File("C:\\"+username);
        File[] listFiles = root.listFiles();
        for (File file : listFiles) {
            if (file.getName().equals(filename)) {
                return file.delete();
            }
        }
        return false;
    }

    public static boolean SignUp(String usern, String pass) {
        for (Info i : Infos) {
            if (i.username.equals(usern)) {
                return false;
            }
        }
        Infos.add(new Info(usern, pass));
        return true;
    }

    public static boolean DocCheck(String name) {
        File root = new File("C:\\");
        File[] listFiles = root.listFiles();
        for (File file : listFiles) {
            if (file.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static boolean Login(String usern, String pass) {
        for (Info i : Infos) {
            if (i.username.equals(usern) && i.password.equals(pass)) {
                return true;
            }
        }
        return false;
    }

    public static boolean UploadFile(String name, File f) {
        try {
            File newf = new File("C:\\" + name + "\\" + f.getName());
            newf.createNewFile();
            
            String ad = f.getName();
            String uzantı = ad.substring(ad.length() - 3);
            
            if (uzantı.equalsIgnoreCase("png") || uzantı.equalsIgnoreCase("jpg")) {
                BufferedImage bi = ImageIO.read(f);
                ImageIO.write(bi, uzantı, newf);
            } else {
                Reader reader = new FileReader(f);
                Writer writer = new FileWriter(newf);

                int bytesRead;
                while ((bytesRead = reader.read()) != -1) {
                    writer.write(bytesRead);
                }
                reader.close();
                writer.close();
            }
            newf = f;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static void createFolder(String username) {
        new File("C:\\" + username).mkdir();
    }
    
    public static String [] FileCheck(String username){
        File file=new File("C:\\" + username);
        String[] list = file.list();
        return list;
    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    // serverdan clietlara mesaj gönderme
    //clieti alıyor ve mesaj olluyor
    public static void Send(SClient cl, Message msg) {

        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
