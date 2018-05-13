/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FtpClient;

import static FtpClient.Client.sInput;
import static GUI.Application.App;
import FtpMessage.Message;
import GUI.Application;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
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
                        boolean flg = (boolean) received.content;
                        if (flg) {
                            App.FileTable.setVisible(false);
                            App.WorkArea.setVisible(true);
                            App.WorkArea.pack();
                            App.WorkArea.repaint();
                        } else {
                            JOptionPane.showMessageDialog(App, "Giriş Bilgileri Hatalıdır...", "Giriş işlemi", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case SignUp:
                        boolean flag = (boolean) received.content;
                        if (flag) {
                            JOptionPane.showMessageDialog(App, "Kayıt Başarıyla Tamamlandı...", "Kayıt işlemi", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(App, "Farklı Kullanıcı Adı Deneyiniz...", "Kayıt işlemi", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case DownloadFile:
                        File download = (File) received.content;
                        boolean D_flag = Client.DownloadFile(download);
                        if (D_flag) {
                            JOptionPane.showMessageDialog(App.WorkArea, "Dosya Indırılmıstır...", "Indirme işlemi", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(App.WorkArea, "Dosya Indirilemedi...", "HATA", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case DeleteFile:
                        Object[] Delete_packet = (Object[]) received.content;
                        boolean Delete_flag = (boolean) Delete_packet[0];
                        if (Delete_flag) {
                            JOptionPane.showMessageDialog(App.WorkArea, "Silme işlemi tamamlanmıştır...", "Silme işlemi", JOptionPane.INFORMATION_MESSAGE);
                            App.dtm.setRowCount(0);
                            String[] F_List = (String[]) Delete_packet[1];
                            Application.filelist = F_List;
                            for (String name : F_List) {
                                App.dtm.addRow(new Object[]{name});
                            }
                            App.FileTable.setVisible(true);
                        }
                        else
                            JOptionPane.showMessageDialog(App.WorkArea, "Dosya Silinemedi...", "Silme işlemi", JOptionPane.ERROR_MESSAGE);
                        break;
                    case UploadFile:
                        boolean up_flag = (boolean) received.content;
                        if (up_flag) {
                            JOptionPane.showMessageDialog(App.WorkArea, "Dosya Başarıyla Yüklendi...", "Yükleme işlemi", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case FileList:
                        App.dtm.setRowCount(0);
                        String[] F_List = (String[]) received.content;
                        Application.filelist = F_List;
                        for (String name : F_List) {
                            App.dtm.addRow(new Object[]{name});
                        }
                        App.FileTable.setVisible(true);
                        break;
                }
                received = null;
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

    public static boolean DownloadFile(File download) {

        try {
            File x = null;
            String path = null;
            JFileChooser FileC = new JFileChooser();
            FileC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int value = FileC.showOpenDialog(App.WorkArea);
            if (value == JFileChooser.APPROVE_OPTION) {
                x = FileC.getSelectedFile();
                path = x.getAbsolutePath();
            }
            File newfile = new File(path + "\\" + download.getName());
            boolean a = newfile.createNewFile();
            if (a) {
                String ad = download.getName();
                String uzantı = ad.substring(ad.length() - 3);

                if (uzantı.equalsIgnoreCase("png") || uzantı.equalsIgnoreCase("jpg")) {
                    BufferedImage bi = ImageIO.read(download);
                    ImageIO.write(bi, uzantı, newfile);
                } else {
                    Reader reader = new FileReader(download);
                    Writer writer = new FileWriter(newfile);

                    int bytesRead;
                    while ((bytesRead = reader.read()) != -1) {
                        writer.write(bytesRead);
                    }
                    reader.close();
                    writer.close();
                }
                newfile = download;
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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
