package Database.UI;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private Image bgImage;

    public HomePanel(mainUI frame) {

        // 🔹 Load Image (FIXED PATH)
        ImageIcon icon = new ImageIcon(getClass().getResource("pexels-gustavo-fring-4971967.jpg"));
        bgImage = icon.getImage();

        setLayout(new BorderLayout());

        // 🔹 Overlay Panel (transparent blue)
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false); // important

        // 🔹 TOP TITLE
        JLabel title = new JLabel("Retail Billing System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        overlay.add(title, BorderLayout.NORTH);

        // 🔹 CENTER CONTENT
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JButton productBtn = createStyledButton("Manage Products");
        JButton billingBtn = createStyledButton("Create Bill");

        productBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        billingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(productBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(billingBtn);
        centerPanel.add(Box.createVerticalGlue());

        overlay.add(centerPanel, BorderLayout.CENTER);

        add(overlay);

        // 🔹 ACTIONS
        productBtn.addActionListener(e -> frame.showPanel("product"));
        billingBtn.addActionListener(e -> frame.showPanel("billing"));
    }

    // 🔥 Draw Background Image + Blue Overlay
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw image
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        // Blue transparent overlay
        g.setColor(new Color(0, 102, 204, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // 🔥 Styled Button
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setMaximumSize(new Dimension(220, 45));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 144, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });

        return btn;
    }
}