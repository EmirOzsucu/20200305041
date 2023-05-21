import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class stokForm extends JFrame {

    private Connection connection;
    private JButton addButton;
    private JButton UpdateButton;
    private JTable table1;
    private JTextField txID;
    private JTextField txProduct;
    private JTextField txQuantity;
    private JButton button1;
    private JPanel stok;
    private JButton DeleteButton;
    private JScrollPane tablo1;


    public stokForm() {
        setContentPane(stok);
        setTitle("Stok Market");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setVisible(true);
        setLocation(400, 50);

        connectToDatabase();
        loadStockDataFromDatabase();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Code for the add button action
                int id = Integer.parseInt(txID.getText());
                String name = txProduct.getText();
                int quantity = Integer.parseInt(txQuantity.getText());

                try {
                    String query = "INSERT INTO stok (ID, NAME, QUANTITY) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.setString(2, name);
                    statement.setInt(3, quantity);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    txID.setText("");
                    txProduct.setText("");
                    txQuantity.setText("");
                    JOptionPane.showMessageDialog(null, "Data saved successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving data.");
                }

            }

        });



        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
                try {
                    String query = "DELETE FROM stok WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    txID.setText("");
                    txProduct.setText("");
                    txQuantity.setText("");
                    JOptionPane.showMessageDialog(null, "Data deleted successfully.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void connectToDatabase() {
        try {
            final String DB_URL = "jdbc:mysql://localhost/20200305041";
            final String USERNAME = "root";
            final String PASSWORD = "12345678";
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStockDataFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM stok";
            PreparedStatement statement1 = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            String[] columnNames = {"ID", "NAME", "QUANTITY"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int quantity = resultSet.getInt("QUANTITY");
                Object[] rowData = {id, name, quantity};
                model.addRow(rowData);
            }
            table1.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            stokForm stokForm = new stokForm();
            stokForm.setVisible(true);
        });
    }
}