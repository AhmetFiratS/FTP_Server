package GUI;

import FtpMessage.Message;
import FtpClient.Client;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import sun.security.krb5.internal.ccache.FileCCacheConstants;

/**
 *
 * @author ahmet
 */
public class Application extends javax.swing.JFrame {

    public static Application App;
    public String U_name;
    public static DefaultTableModel dtm;
    public static String [] filelist;
    
    public Application() {
        
        initComponents();
        App=this;
        App.pack();
        App.repaint();
        App.WorkArea.setVisible(false);
        FileTable.setVisible(false);
        dtm=new DefaultTableModel();
        dtm.setColumnIdentifiers(new Object[] {"Dosya Adı"});
        FileTable.setModel(dtm);
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WorkArea = new javax.swing.JFrame();
        jLabel3 = new javax.swing.JLabel();
        toFileChooser = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_FileName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        FileTable = new javax.swing.JTable();
        FileChecker = new javax.swing.JButton();
        Download = new javax.swing.JButton();
        Remove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        Btn_Giris = new javax.swing.JButton();
        Btn_Kayit = new javax.swing.JButton();
        txt_Password = new javax.swing.JPasswordField();

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Dosya Yükleme :");

        toFileChooser.setText("Dosya Seçim Ekranı");
        toFileChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toFileChooserMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Yüklenen Dosya :");

        FileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(FileTable);

        FileChecker.setText("Yüklü Dosyalar");
        FileChecker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FileCheckerMouseClicked(evt);
            }
        });

        Download.setText("Seçili Dosyayı İNDİR");
        Download.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DownloadMouseClicked(evt);
            }
        });

        Remove.setText("Seçili Dosyayı SİL");
        Remove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemoveMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout WorkAreaLayout = new javax.swing.GroupLayout(WorkArea.getContentPane());
        WorkArea.getContentPane().setLayout(WorkAreaLayout);
        WorkAreaLayout.setHorizontalGroup(
            WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WorkAreaLayout.createSequentialGroup()
                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(WorkAreaLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(WorkAreaLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(FileChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(WorkAreaLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(WorkAreaLayout.createSequentialGroup()
                                .addComponent(Download, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Remove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(WorkAreaLayout.createSequentialGroup()
                                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(toFileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_FileName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        WorkAreaLayout.setVerticalGroup(
            WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WorkAreaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(toFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txt_FileName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FileChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(WorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Download, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Kullanıcı Adı :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Şifre :");

        Btn_Giris.setText("Giriş Yap");
        Btn_Giris.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_GirisMouseClicked(evt);
            }
        });

        Btn_Kayit.setText("Kayıt Ol");
        Btn_Kayit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_KayitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Btn_Giris, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(Btn_Kayit, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_Password)
                            .addComponent(txt_Username, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Giris, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Kayit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_KayitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_KayitMouseClicked
        Client.Start("127.0.0.1", 2000);
        String username=txt_Username.getText();
        String password=txt_Password.getText();
        String [] packet = new String [2];
        packet[0]=username;
        packet[1]=password;
        Message msg = new Message(Message.Message_Type.SignUp);
        msg.content=packet;
        Client.Send(msg);
    }//GEN-LAST:event_Btn_KayitMouseClicked

    private void toFileChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toFileChooserMouseClicked
       File f=null;
       JFileChooser FileC = new JFileChooser();
       int value =FileC.showOpenDialog(WorkArea);
       if (value ==JFileChooser.APPROVE_OPTION){
           f=FileC.getSelectedFile();
           txt_FileName.setText(f.getName());
       }
       Message msg = new Message(Message.Message_Type.UploadFile);
       Object [] container=new Object [2];
       container[0]=U_name;
       container[1]=f;
       msg.content=container;
       Client.Send(msg);
    }//GEN-LAST:event_toFileChooserMouseClicked

    private void Btn_GirisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_GirisMouseClicked
       String username=txt_Username.getText();
       U_name=txt_Username.getText();
       String password=txt_Password.getText();
       String [] packet = new String [2];
       packet[0]=username;
       packet[1]=password;
       Message msg = new Message(Message.Message_Type.Login);
       msg.content=packet;
       Client.Send(msg);       
    }//GEN-LAST:event_Btn_GirisMouseClicked

    private void FileCheckerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FileCheckerMouseClicked
        String username=U_name;
        Message msg = new Message(Message.Message_Type.FileList);
        msg.content=username;
        Client.Send(msg);
    }//GEN-LAST:event_FileCheckerMouseClicked

    private void DownloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DownloadMouseClicked
        String username=U_name;
        int count=FileTable.getSelectedRow();
        String selectedFile=filelist[count];
        
        Message msg = new Message(Message.Message_Type.DownloadFile);
        String [] packet = {username,selectedFile};
        msg.content=packet;
        Client.Send(msg);
    }//GEN-LAST:event_DownloadMouseClicked

    private void RemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveMouseClicked
        String username=U_name;
        int count=FileTable.getSelectedRow();
        String selectedFile=filelist[count];
        
        Message msg = new Message(Message.Message_Type.DeleteFile);
        String [] packet = {username,selectedFile};
        msg.content=packet;
        Client.Send(msg);
    }//GEN-LAST:event_RemoveMouseClicked
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Giris;
    private javax.swing.JButton Btn_Kayit;
    private javax.swing.JButton Download;
    private javax.swing.JButton FileChecker;
    public javax.swing.JTable FileTable;
    private javax.swing.JButton Remove;
    public javax.swing.JFrame WorkArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton toFileChooser;
    private javax.swing.JLabel txt_FileName;
    private javax.swing.JPasswordField txt_Password;
    private javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}
