package Database.UI;

import javax.swing.*;
import java.awt.*;

public class mainUI extends JFrame {

    CardLayout cardLayout;
    JPanel container;

    public mainUI() {
        setTitle("Retail Billing System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Panels
        HomePanel home = new HomePanel(this);
        ProductPanel product = new ProductPanel(this);
        BillingPanel billing = new BillingPanel(this);

        container.add(home, "home");
        container.add(product, "product");
        container.add(billing, "billing");

        add(container);
        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public static void main(String[] args) {
        new mainUI();
    }
}