/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpsrvrserver;

import FtpMessage.Message;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmet
 */
public class SClient {
    
    int id;
    int userNo;
    public String name = "NoName";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    //clientten gelenleri dinleme threadi
    Listen listenThread;
    
    
    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        
    }
    
     //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    class Listen extends Thread {

        SClient TheClient;

        //thread nesne alması için yapıcı metod
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı olduğu sürece dönsün
            while (TheClient.soket.isConnected()) {
                try {
                    //mesajı bekleyen kod satırı
                    Message received = (Message) (TheClient.sInput.readObject());
                    //mesaj gelirse bu satıra geçer
                    //mesaj tipine göre işlemlere ayır
                    switch (received.type) {
                        
                        case DownloadFile:
                            String [] Down_packet=(String []) received.content;
                            File downloadFile=Server.downloadfile(Down_packet[0],Down_packet[1]);
                            Message down_msg=new Message(Message.Message_Type.DownloadFile);
                            down_msg.content=downloadFile;
                            Server.Send(TheClient, down_msg);
                            break;
                        case Login:
                            String [] Lpacket = (String[]) received.content;
                            boolean flg=Server.Login(Lpacket[0], Lpacket[1]);
                            Message msg1 = new Message(Message.Message_Type.Login);
                            msg1.content=flg;
                            Server.Send(TheClient, msg1);
                            break;
                        case SignUp:
                            String [] Spacket =(String[]) received.content;
                            boolean flag=Server.SignUp(Spacket[0], Spacket[1]);
                            Message msg = new Message(Message.Message_Type.SignUp);
                            msg.content=flag;
                            Server.Send(TheClient, msg);
                            if(flag && Server.DocCheck(Spacket[0]))
                                Server.createFolder(Spacket[0]);
                            break;
                        case UploadFile:
                            Object [] container=(Object []) received.content;
                            String name=(String) container[0];
                            File upload=(File) container[1];
                            boolean x=Server.UploadFile(name, upload);
                            Message up_msg = new Message(Message.Message_Type.UploadFile);
                            up_msg.content=x;
                            Server.Send(TheClient, up_msg);
                            break;
                        case FileList:
                            String FL_username=(String) received.content;
                            String [] Flist=Server.FileCheck(FL_username);
                            Message FL_msg = new Message(Message.Message_Type.FileList);
                            FL_msg.content=Flist;
                            Server.Send(TheClient, FL_msg);
                            break;
                        case DeleteFile:
                            String [] Delete_pack =(String []) received.content;
                            boolean Delete_flag=Server.DeleteFile(Delete_pack[0], Delete_pack[1]);
                            String [] newFlist=Server.FileCheck(Delete_pack[0]);
                            Message Delete_msg=new Message(Message.Message_Type.DeleteFile);
                            Object [] D_container={Delete_flag , newFlist};
                            Delete_msg.content=D_container;
                            Server.Send(TheClient, Delete_msg);
                            break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);
                }
            }
        }
    }
    
}
