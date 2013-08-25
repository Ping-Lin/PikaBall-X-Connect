import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

class Network extends Frame
{
    public JFrame frame;
    private JButton clientBtn, serverBtn;
    private JTextField IP, port;
    private JLabel hint, message;
    private Server serverSkt;  // 伺服端連線處理執行緒
    private Client clientSkt;  // 客戶端連線處理執行緒
    private boolean isServer; // 是否成為伺服端
    private String yourIP;

    public Network()
    {
        getIP();
        frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));  // 版面配置
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        hint = new JLabel("請先輸入IP及port後再選擇連線端");
        hint.setPreferredSize(new Dimension(250,30));
        hint.setFont(new Font("微軟正黑體",Font.BOLD,16));
        frame.add(hint);
        
        IP = new JTextField(yourIP);
        IP.setPreferredSize(new Dimension(250,30));
        IP.setFont(new Font("微軟正黑體",Font.BOLD,16));
        frame.add(IP);
        
        port = new JTextField("port");
        port.setPreferredSize(new Dimension(250,30));
        port.setFont(new Font("微軟正黑體",Font.BOLD,16));
        frame.add(port);
        
        serverBtn = new JButton("Server");
        serverBtn.setPreferredSize(new Dimension(120,40));
        serverBtn.setFont(new Font("微軟正黑體",Font.BOLD,16));
        serverBtn.addMouseListener(new serverListener());
        frame.add(serverBtn);
        
        clientBtn = new JButton("Client");
        clientBtn.setPreferredSize(new Dimension(120,40));
        clientBtn.setFont(new Font("微軟正黑體",Font.BOLD,16));
        clientBtn.addMouseListener(new clientListener());
        frame.add(clientBtn); 
        
        message = new JLabel("尚未建立連線");
        message.setPreferredSize(new Dimension(250,30));
        message.setFont(new Font("微軟正黑體",Font.BOLD,16));
        message.setHorizontalTextPosition(JLabel.CENTER);
        message.setVerticalTextPosition(JLabel.BOTTOM);
        frame.add(message);
    }
    
    private void getIP()
    {
        try 
        {
            InetAddress myHost = InetAddress.getLocalHost();
            yourIP = myHost.getHostAddress();
        } 
        catch (UnknownHostException e) 
        {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.toString(),"不明的主機的位址", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    private class serverListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            setServer();
        }
    }
    
    private class clientListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            setClient();
        }
    }
    
    private void setServer()
    {
        int Port = Integer.parseInt(port.getText()); // 取得指定的連接埠
        
        serverSkt = new Server(Port); // 建立伺服端連線執行緒
        serverSkt.setMessageObserver(this);
        serverSkt.start(); // 啟動執行緒傾聽連線
        isServer = true; // 標示成為伺服端
        setGUIState(false); // 按鈕與上面文字欄位失效
    }
    
    private void setClient()
    {
        int Port = Integer.parseInt(port.getText()); // 取得指定的IP與連接埠
        
        clientSkt = new Client(IP.getText(), Port); // 建立客戶端連線執行緒
        clientSkt.setMessageObserver(this);
        clientSkt.start(); // 啟動執行緒進行連線
        setGUIState(false); // 按鈕與上面文字欄位失效
    }
    
    public void setGUIState(boolean state) 
    {
        IP.setEnabled(state);
        port.setEnabled(state);
        serverBtn.setEnabled(state);
        clientBtn.setEnabled(state);
    }
    
    public void update() 
    {
        if(isServer)
            message.setText(serverSkt.getMessage());
        else
            message.setText(clientSkt.getMessage());
    }
}
