import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
    private static int offsetX;
    private static int offsetY;
    public Window() {
        JFrame frame = new JFrame("Pepemotes");
        frame.setSize(400, 600);
        frame.setResizable(false);
        frame.setUndecorated(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("assets/textures/logo/logo.png");
        frame.setIconImage(icon.getImage());
        frame.setLocationRelativeTo(null);

        new Tray(frame);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() <= 400 && e.getY() <= 30) {
                    offsetX = e.getX();
                    offsetY = e.getY();
                }
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getX() <= 400 && e.getY() <= 30) {
                    frame.setLocation(frame.getX() + e.getX() - offsetX, frame.getY() + e.getY() - offsetY);
                }
            }
        });

        Graphics graphics = new Graphics(frame);

        graphics.Rectangle(0, 0, 400, 30, "3B3733", 0);
        graphics.Image(5, 5, 20, 20, "assets/textures/logo/logo.png", 1);

        graphics.Text(36, 20, 16, "F7B947", "San Francisco", "Тестик", 5);

        // Кнопка скрытия окна
        graphics.HideButton(373, 3, 24, 24, "", 4);
        graphics.ImageW(373, 3, 24, "assets/textures/icons/hide.png", 4);

        frame.pack();
        frame.setVisible(true);
    }
}