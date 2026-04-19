package Database.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import Database.DAO.productDao;
import Database.DAO.BillDao;
import Database.GetterSetter.Product;

public class BillingPanel extends JPanel {

    JTable productTable, cartTable;
    DefaultTableModel productModel, cartModel;

    JTextField qtyField;
    JLabel totalLabel;

    double total = 0;
    private Image bgImage;

    public BillingPanel(mainUI frame) {

        ImageIcon icon = new ImageIcon(getClass().getResource("pexels-gustavo-fring-4971967.jpg"));
        bgImage = icon.getImage();

        setLayout(new BorderLayout());

        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);

        // 🔙 TOP BAR
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        JButton backBtn = new JButton("← Back");
        styleButton(backBtn);

        JLabel title = new JLabel("Billing System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);

        overlay.add(topBar, BorderLayout.NORTH);

        backBtn.addActionListener(e -> frame.showPanel("home"));

        //  MAIN PANEL
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // ================= LEFT PANEL =================
        JPanel leftPanel = createCardPanel("Products");

        productModel = new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        productTable = new JTable(productModel);
        styleTable(productTable);

        JScrollPane sp1 = new JScrollPane(productTable);
        leftPanel.add(sp1, BorderLayout.CENTER);

        JPanel bottomLeft = new JPanel();
        bottomLeft.setOpaque(false);

        qtyField = new JTextField(5);
        qtyField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton addBtn = new JButton("Add to Cart");
        styleButton(addBtn);

        bottomLeft.add(new JLabel("Qty:"));
        bottomLeft.add(qtyField);
        bottomLeft.add(addBtn);

        leftPanel.add(bottomLeft, BorderLayout.SOUTH);

        // ================= RIGHT PANEL =================
        JPanel rightPanel = createCardPanel("Cart");

        cartModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Qty", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        cartTable = new JTable(cartModel);
        styleTable(cartTable);

        JScrollPane sp2 = new JScrollPane(cartTable);
        rightPanel.add(sp2, BorderLayout.CENTER);

        // RIGHT CLICK REMOVE
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove Item");
        popupMenu.add(removeItem);
        cartTable.setComponentPopupMenu(popupMenu);

        cartTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = cartTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    cartTable.setRowSelectionInterval(row, row);
                }
            }
        });

        removeItem.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row != -1) {
                cartModel.removeRow(row);
                updateTotal();
            }
        });

        //  BOTTOM RIGHT (FIXED UI)
        JPanel bottomRight = new JPanel(new BorderLayout());
        bottomRight.setOpaque(false);

        totalLabel = new JLabel("Total: 0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton saveBtn = new JButton("Save Bill");
        styleButton(saveBtn);

        JButton removeBtn = new JButton("Remove");
        styleButton(removeBtn);
        removeBtn.setBackground(new Color(220, 53, 69)); // red

        // Button panel (fix alignment)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setOpaque(false);

        removeBtn.setPreferredSize(new Dimension(120, 35));
        saveBtn.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(removeBtn);
        buttonPanel.add(saveBtn);

        bottomRight.add(totalLabel, BorderLayout.WEST);
        bottomRight.add(buttonPanel, BorderLayout.EAST);

        rightPanel.add(bottomRight, BorderLayout.SOUTH);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        overlay.add(mainPanel, BorderLayout.CENTER);
        add(overlay);

        loadProducts();

        // ================= ACTIONS =================

        addBtn.addActionListener(e -> {

            int row = productTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select product!");
                return;
            }

            try {
                int id = (int) productModel.getValueAt(row, 0);
                String name = (String) productModel.getValueAt(row, 1);
                double price = (double) productModel.getValueAt(row, 2);

                int qty = Integer.parseInt(qtyField.getText());

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be > 0");
                    return;
                }

                boolean found = false;

                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    int existingId = (int) cartModel.getValueAt(i, 0);

                    if (existingId == id) {
                        int existingQty = (int) cartModel.getValueAt(i, 3);
                        int newQty = existingQty + qty;

                        double newTotal = newQty * price;

                        cartModel.setValueAt(newQty, i, 3);
                        cartModel.setValueAt(newTotal, i, 4);

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    double itemTotal = price * qty;
                    cartModel.addRow(new Object[]{id, name, price, qty, itemTotal});
                }

                updateTotal();
                qtyField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Enter valid quantity!");
            }
        });

        removeBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select item to remove!");
                return;
            }

            cartModel.removeRow(row);
            updateTotal();
        });

        saveBtn.addActionListener(e -> {

            if (cartModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Cart is empty!");
                return;
            }

            BillDao billDao = new BillDao();
            int billId = billDao.createBill(total);

            if (billId == -1) {
                JOptionPane.showMessageDialog(null, "Error saving bill!");
                return;
            }

            for (int i = 0; i < cartModel.getRowCount(); i++) {
                int productId = (int) cartModel.getValueAt(i, 0);
                int qty = (int) cartModel.getValueAt(i, 3);

                billDao.addBillItem(billId, productId, qty);
            }

            JOptionPane.showMessageDialog(null, "Bill Saved!");

            cartModel.setRowCount(0);
            total = 0;
            totalLabel.setText("Total: 0.0");
        });
    }

    private void updateTotal() {
        total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            total += (double) cartModel.getValueAt(i, 4);
        }
        totalLabel.setText("Total: " + total);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 102, 204, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255, 220));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16)
        ));
        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
    }

    public void loadProducts() {
        productModel.setRowCount(0);

        productDao dao = new productDao();
        List<Product> list = dao.getAllProducts();

        for (Product p : list) {
            productModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getPrice()
            });
        }
    }
}