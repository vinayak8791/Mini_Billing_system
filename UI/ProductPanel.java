package Database.UI;

import javax.swing.*;
import java.awt.*;

import Database.DAO.productDao;
import Database.GetterSetter.Product;

public class ProductPanel extends JPanel {

    private Image bgImage;
    JTextField nameField, priceField;

    public ProductPanel(mainUI frame) {

        ImageIcon icon = new ImageIcon(getClass().getResource("pexels-gustavo-fring-4971967.jpg"));
        bgImage = icon.getImage();

        setLayout(new BorderLayout());

        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false);

        // 🔙 Back Button
        JButton backBtn = new JButton("← Back");
        styleButton(backBtn);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(backBtn);

        overlay.add(topPanel, BorderLayout.NORTH);

        //  BIG FORM PANEL
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(255, 255, 255, 220));
        formCard.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        formCard.setMaximumSize(new Dimension(600, 500)); // BIG WIDTH

        JLabel title = new JLabel("Add New Product");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36)); // BIG TITLE
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(title);
        formCard.add(Box.createRigidArea(new Dimension(0, 40)));

        //  Name Field
        nameField = new JTextField();
        styleTextField(nameField, "Product Name");

        //  Price Field
        priceField = new JTextField();
        styleTextField(priceField, "Product Price");

        formCard.add(nameField);
        formCard.add(Box.createRigidArea(new Dimension(0, 25)));
        formCard.add(priceField);
        formCard.add(Box.createRigidArea(new Dimension(0, 40)));

        //  Add Button
        JButton addBtn = new JButton("Add Product");
        styleButton(addBtn);
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(addBtn);

        // Center Wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(formCard);

        overlay.add(centerWrapper, BorderLayout.CENTER);

        add(overlay);

        //  ACTIONS
        backBtn.addActionListener(e -> frame.showPanel("home"));

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                Product p = new Product(name, price);
                new productDao().addProduct(p);

                JOptionPane.showMessageDialog(null, "Product Added!");

                nameField.setText("");
                priceField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });
    }

    //  Background + Overlay
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        g.setColor(new Color(0, 102, 204, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    //  BIG BUTTON STYLE
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20)); // BIG TEXT
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(260, 60)); // BIG BUTTON

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 144, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });
    }

    //  BIG TEXT FIELD STYLE
    private void styleTextField(JTextField field, String title) {
        field.setMaximumSize(new Dimension(450, 65)); // BIG FIELD
        field.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // BIG TEXT

        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)),
                title,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16)
        ));
    }
}