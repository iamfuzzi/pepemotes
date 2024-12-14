import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Tray {
    private SystemTray tray;
    private TrayIcon trayIcon;

    public Tray(JFrame frame) {
        // Проверяем, поддерживается ли системный трей
        if (!SystemTray.isSupported()) {
            System.out.println("Системный трей не поддерживается.");
            return;
        }

        // Создаем иконку для трея
        Image image = Toolkit.getDefaultToolkit().getImage("assets/icon.png");
        trayIcon = new TrayIcon(image, "Программа");

        // Создаем контекстное меню
        PopupMenu popupMenu = new PopupMenu();

        // Создаем пункт "Открыть"
        MenuItem openItem = new MenuItem("Открыть");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Показываем окно при нажатии
                SwingUtilities.invokeLater(() -> frame.setVisible(true));
            }
        });
        popupMenu.add(openItem);

        // Создаем пункт "Выключить"
        MenuItem exitItem = new MenuItem("Выключить");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Завершаем выполнение программы
                System.exit(0);
            }
        });
        popupMenu.add(exitItem);

        // Устанавливаем контекстное меню для иконки
        trayIcon.setPopupMenu(popupMenu);
        trayIcon.setImageAutoSize(true); // Автоматически подгоняем размер иконки

        // Добавляем иконку в системный трей
        tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Не удалось добавить иконку в системный трей.");
        }
    }
}