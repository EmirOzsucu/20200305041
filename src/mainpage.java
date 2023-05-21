import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainpage extends JFrame {
    private JPanel panel1;
    private JButton ExitButton;
    private JButton StokButton;


    public mainpage() {
        setTitle("Main Page");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,700);
        setLocation(400,50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    ExitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);

        }
    });


    StokButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            stokForm stokForm = new stokForm();
            stokForm.setVisible(true);
            setVisible(false);

        }
    });
    setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainpage mainpage = new mainpage();
            }
        });
    }
    }
