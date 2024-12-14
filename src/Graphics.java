import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Graphics {
    private JLayeredPane layeredPane;
    private JFrame frame;
    private Map<String, String> fonts;
    public Graphics(JFrame frame) {
        this.frame = frame;
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        frame.add(layeredPane);

        fonts = new HashMap<>();
        fonts.put("Lineyka", "assets/fonts/Lineyka.otf");
        fonts.put("San Francisco", "assets/fonts/SFProText-Bold.ttf");
    }

    public void Rectangle(int x, int y, int w, int h, String hex, int layer) {
        Color color = new Color(Integer.parseInt(hex, 16));
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(0, 0, w, h);
            }
        };
        panel.setBounds(x, y, w, h);
        layeredPane.add(panel, Integer.valueOf(layer));
    }
    public void Image(int x, int y, int w, int h, String path, int layer) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon(path);
                g.drawImage(image.getImage(), 0, 0, w, h, null);
            }
        };
        panel.setBounds(x, y, w, h);
        panel.setOpaque(false); // Делаем панель прозрачной
        layeredPane.add(panel, Integer.valueOf(layer));
    }
    public void ImageW(int x, int y, int s, String path, int layer) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();

        int originalWidth = imageIcon.getIconWidth();
        int originalHeight = imageIcon.getIconHeight();

        int newHeight = (int) ((double) originalHeight / originalWidth * s);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, s, newHeight, null);
            }
        };

        panel.setBounds(x, y, s, newHeight);
        panel.setOpaque(false); // Делаем панель прозрачной
        layeredPane.add(panel, Integer.valueOf(layer));
    }
    public void HideButton(int x, int y, int w, int h, String text, int layer) {
        JButton button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.setOpaque(false); // Делаем кнопку прозрачной
        button.setContentAreaFilled(false); // Убираем заливку
        button.setBorderPainted(false); // Убираем рамку

        // Добавляем слушатель для кнопки
        button.addActionListener(e -> frame.setVisible(false)); // Скрываем окно при нажатии

        layeredPane.add(button, Integer.valueOf(layer));
    }
    public void Text(int x, int y, int size, String hex, String font, String content, int layer) {
        Color color = new Color(Integer.parseInt(hex, 16));
        String fontPath = fonts.get(font);
        if (fontPath == null) {
            System.err.println("Шрифт не найден: " + font);
            return; // Если шрифт не найден, выходим из метода
        }

        try {
            // Загружаем шрифт из файла
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont((float) size);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    super.paintComponent(g);
                    g.setFont(customFont); // Устанавливаем загруженный шрифт
                    g.setColor(color); // Устанавливаем цвет текста
                    g.drawString(content, x, y); // Рисуем текст
                }
            };

            // Устанавливаем размеры панели, чтобы текст был виден
            panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            panel.setOpaque(false); // Делаем панель прозрачной
            layeredPane.add(panel, Integer.valueOf(layer));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); // Обработка ошибок при загрузке шрифта
        }
    }
}