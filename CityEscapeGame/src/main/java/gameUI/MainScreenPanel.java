package gameUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MainScreenPanel creates a simple GUI with a title and two buttons ("PLAY" and "EXIT").
 * Uses CardLayout to allow switching between "Main" and "Game" screens.
 */
public class MainScreenPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color BUTTON_COLOR = Color.DARK_GRAY;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 36);

    public MainScreenPanel(ActionListener actionListener) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Title setup
        JLabel titleLabel = createTitleLabel("CITY ESCAPE GAME");
        add(titleLabel, BorderLayout.NORTH);

        // Center panel setup
        JPanel centerPanel = createPanelWithBackground(BACKGROUND_COLOR);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Button panel setup
        JPanel buttonPanel = createPanelWithBackground(BACKGROUND_COLOR);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Create and add buttons
        JLabel playLabel = createButtonLabel("PLAY");
        JLabel exitLabel = createButtonLabel("EXIT");

        playLabel.addMouseListener(createPlayMouseListener(actionListener));
        exitLabel.addMouseListener(createExitMouseListener());

        buttonPanel.add(playLabel);
        buttonPanel.add(exitLabel);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createPanelWithBackground(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_COLOR);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(800, 150));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setGradientBackground(label, Color.BLUE, Color.CYAN);
        return label;
    }

    private JLabel createButtonLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(BUTTON_FONT);
        label.setForeground(TEXT_COLOR);
        label.setBackground(BUTTON_COLOR);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(120, 60));
        label.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 2));
        return label;
    }

    private void setGradientBackground(JLabel label, Color startColor, Color endColor) {
        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, c.getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                super.paint(g, c);
            }
        });
    }

    private MouseAdapter createPlayMouseListener(ActionListener actionListener) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        };
    }

    private MouseAdapter createExitMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        };
    }
}
