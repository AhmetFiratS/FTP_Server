/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FtpClient;

import static FtpClient.Client.sInput;
import static GUI.Interface.App;
import FtpMessage.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


class Listen extends Thread {

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case Login:
                        boolean flg=(boolean)received.content;
                        if(flg){
                            App.WorkArea.setVisible(true);
                            App.WorkArea.pack();
                            App.WorkArea.repaint();}
                        else
                            JOptionPane.showMessageDialog(App, "Giriş Bilgileri Hatalıdır...", "Giriş işlemi",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case SignUp:
                        boolean flag=(boolean)received.content;
                        if(flag)
                            JOptionPane.showMessageDialog(App, "Kayıt Başarıyla Tamamlandı...", "Kayıt işlemi",JOptionPane.INFORMATION_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(App, "Farklı Kullanıcı Adı Deneyiniz...", "Kayıt işlemi",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case DownloadFile:
//                    int [] x= (int[]) received.content;
//                    changeImage(x);
//                    Game.ThisGame.click=true;
                        /*int s[] = (int[])received.content;
                        System.out.println("Gelen==>  "+s[0]+"   "+s[1]);*/
                    case Disconnect:
                        break;
                    case UploadFile:
                        boolean up_flag=(boolean) received.content;
                        if(up_flag)
                            JOptionPane.showMessageDialog(App.WorkArea, "Dosya Başarıyla Yüklendi...", "Yükleme işlemi",JOptionPane.INFORMATION_MESSAGE);
//                    int input=0;
//                    if((boolean)received.content)
//                        input= JOptionPane.showOptionDialog(Game.ThisGame.Table, "TEBRİKLER\nKazandınız...", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
//                    if(input == JOptionPane.OK_OPTION || input==JOptionPane.CANCEL_OPTION)
//                        System.exit(0);
                        break;
                    case FileList:
//                    int input1=0;
//                    if((boolean)received.content)
//                        input1= JOptionPane.showOptionDialog(Game.ThisGame.Table, "Kaybettiniz...", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
//                    if(input1 == JOptionPane.OK_OPTION || input1==JOptionPane.CANCEL_OPTION)
//                        System.exit(0);
                        break;
                        
                }
                received=null;
            } catch (IOException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;
    public static int UserNumber;
    
    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
//            Message msg = new Message(Message.Message_Type.Name);
//            msg.content = Game.ThisGame.Username.getText();
//            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        public static void Display(String msg) {

        System.out.println(msg);

    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
