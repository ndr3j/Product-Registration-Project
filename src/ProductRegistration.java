import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProductRegistration{
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField txtPid;
    private JButton updateButton;
    private JLabel Product;
    private JLabel Price;
    private JTextField txtQuantity;
    private JButton Search;
    private JLabel Quantity;
    private JLabel ProductID;


    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductRegistration");
        frame.setContentPane(new ProductRegistration().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection connection;
    PreparedStatement preparedStatement;


    public void Connect() {

        try {

           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "1234");

            System.out.println("Success");

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }

    public ProductRegistration() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                        String Product;
                        int Price;
                        int Quantity;
                Product = txtName.getText();
                Price = Integer.parseInt(txtPrice.getText());
                Quantity = Integer.parseInt(txtQuantity.getText());



                try {
                    preparedStatement = connection.prepareStatement("insert into products (Product, Price, Quantity) values (?,?,?)");
                    preparedStatement.setString(1, Product);
                    preparedStatement.setInt(2, Price);
                    preparedStatement.setInt(3, Quantity);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog( null, "Product added");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQuantity.setText("");
                    txtName.requestFocus();


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Connect();


                try {
                    String ProductID = txtPid.getText();

                    preparedStatement = connection.prepareStatement("select Product, Price, Quantity from products where ProductID = ?");
                    preparedStatement.setString(1, ProductID);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if(resultSet.next()){

                        String Product = resultSet.getString(1);
                        String Price = resultSet.getString(2);
                        String Quantity = resultSet.getString(3);

                        txtName.setText(Product);
                        txtPrice.setText(Price);
                        txtQuantity.setText(Quantity);
                    }

                    else {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQuantity.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid product ID");
                    }

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connect();

                String Product = txtName.getText();
                String Price = txtPrice.getText();
                String Quantity = txtQuantity.getText();
                String ProductID = txtPid.getText();

                try{
                    preparedStatement =connection.prepareStatement("update products set Product = ?, Price = ?, Quantity = ? where ProductID = ?");
                    preparedStatement.setString(1, Product);
                    preparedStatement.setString(2, Price);
                    preparedStatement.setString(3, Quantity);
                    preparedStatement.setString(4, ProductID);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQuantity.setText("");
                    txtPid.setText("");
                    txtName.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Connect();

                String Did;

                Did = txtPid.getText();

                try {
                    preparedStatement = connection.prepareStatement("delete from products where ProductID = ?");
                    preparedStatement.setString(1, Did);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record deleted");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQuantity.setText("");
                    txtPid.setText("");
                    txtName.requestFocus();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }


}
