package bulletinBoardService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Window extends JFrame {
    private JTextField textFieldMsg;
    private JTextField GROUPTextField;
    private JTextField PORTTextField;
    private JTextField NAMETextField;
    private JButton shutdownButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private JButton clearButton;
    private JButton sendButton;
    private JPanel Main;
    private JTextArea textArea;
    private Messenger messenger = null;


    private InetAddress addr;
    private int port;
    private String name;

    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UITasks ui = (UITasks) Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{UITasks.class},
                        new EDTInvocationHandler(new UITasksImpl()));
                try {
                    addr = InetAddress.getByName(GROUPTextField.getText());
                } catch (UnknownHostException ex) {
                    throw new RuntimeException(ex);
                }
                port = Integer.parseInt(PORTTextField.getText());
                name = NAMETextField.getText();
                System.out.println(name);
                messenger = new MessenderImpl(addr, port, name, ui);
                messenger.start();
            }
        });
        sendButton.addActionListener(e -> {
            if(messenger != null) messenger.send();
        });
        clearButton.addActionListener(e -> {
            textArea.setText("");
        });
        disconnectButton.addActionListener(e -> {
            messenger = null;
        });
        shutdownButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Your window name");
        frame.setContentPane(new Window().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class UITasksImpl implements UITasks {
        @Override
        public String getMessage() {
            String res = textFieldMsg.getText();
            textFieldMsg.setText("");
            return res;
        }

        @Override
        public void setText(String txt) {
            textArea.append(txt + "\n");
        }
    }

}
