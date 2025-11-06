import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Model Class for Product
class Product {
    String id;
    String name;
    double price;
    int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotal() {
        return price * quantity;
    }
}

// Main Billing System
public class E_BillingSystem extends JFrame {

    private JTextField txtProductId, txtProductName, txtPrice, txtQuantity;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblGrandTotal;
    private List<Product> productList;

    public E_BillingSystem() {
        // Window setup
        setTitle("🧾 E-Billing System for Retail Store");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        productList = new ArrayList<>();

        // ===== Header =====
        JLabel title = new JLabel("E-BILLING SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setOpaque(true);
        title.setBackground(new Color(52, 152, 219));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(900, 60));
        add(title, BorderLayout.NORTH);

        // ===== Input Section =====
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Product Details"));
        inputPanel.setPreferredSize(new Dimension(900, 100));

        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(txtProductId);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(txtProductName);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(txtQuantity);

        add(inputPanel, BorderLayout.NORTH);

        // ===== Table Section =====
        model = new DefaultTableModel(new String[]{"Product ID", "Product Name", "Price", "Quantity", "Total"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Billing Items"));
        add(scrollPane, BorderLayout.CENTER);

        // ===== Buttons + Total =====
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // Buttons on left
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JButton btnAdd = new JButton("Add Product");
        JButton btnRemove = new JButton("Remove Selected");
        JButton btnClear = new JButton("Clear All");
        btnPanel.add(btnAdd);
        btnPanel.add(btnRemove);
        btnPanel.add(btnClear);

        bottomPanel.add(btnPanel, BorderLayout.WEST);

        // Total label on right
        lblGrandTotal = new JLabel("Grand Total: ₹0.00", JLabel.RIGHT);
        lblGrandTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblGrandTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(lblGrandTotal, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====

        // Add Product
        btnAdd.addActionListener(e -> addProduct());

        // Remove Selected Row
        btnRemove.addActionListener(e -> removeSelectedProduct());

        // Clear All
        btnClear.addActionListener(e -> clearAllProducts());
    }

    // Add Product to Table + Backend
    private void addProduct() {
        try {
            String id = txtProductId.getText().trim();
            String name = txtProductName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            Product p = new Product(id, name, price, quantity);
            productList.add(p);

            model.addRow(new Object[]{p.id, p.name, p.price, p.quantity, p.getTotal()});
            updateGrandTotal();
            clearInputFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers for Price and Quantity.");
        }
    }

    // Remove selected product
    private void removeSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            productList.remove(selectedRow);
            model.removeRow(selectedRow);
            updateGrandTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to remove.");
        }
    }

    // Clear all products
    private void clearAllProducts() {
        productList.clear();
        model.setRowCount(0);
        updateGrandTotal();
    }

    // Update total label
    private void updateGrandTotal() {
        double sum = 0;
        for (Product p : productList) {
            sum += p.getTotal();
        }
        lblGrandTotal.setText(String.format("Grand Total: ₹%.2f", sum));
    }

    // Clear text fields
    private void clearInputFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new E_BillingSystem().setVisible(true);
        });
    }
}
