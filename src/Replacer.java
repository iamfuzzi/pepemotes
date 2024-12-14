import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Replacer implements NativeKeyListener {

    private List<Integer> keySequence = new ArrayList<>();
    private final Map<String, String> actions = new HashMap<>();

    public Replacer() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            initializeActions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeActions() {
        actions.put("otagging", "https://cdn.7tv.app/emote/01J0PYKYFR00068734GKCYD6P4/4x.gif");
        actions.put("shocked", "https://cdn.7tv.app/emote/01GSZXG0B000087DXYC0P80SQT/4x.gif");
        actions.put("rilobulion", "https://cdn.7tv.app/emote/01G1H198F80005G1MWWMPGT05K/4x.gif");
        actions.put("buhflip", "https://cdn.7tv.app/emote/01J4PE2RKG0004SVBK6PNC37T6/4x.gif");
        actions.put("urayaderka", "https://cdn.7tv.app/emote/01G9TPSGDG000AGTWTH4BHTCTY/4x.gif");
        actions.put("gde", "https://cdn.7tv.app/emote/01GPC2MPYR0000TJ7CCYB26WBX/4x.gif");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        keySequence.add(e.getKeyCode());
        checkSequence();
    }

    private void checkSequence() {
        for (Map.Entry<String, String> entry : actions.entrySet()) {
            String targetSequence = entry.getKey();
            if (keySequence.size() >= targetSequence.length()) {
                boolean match = true;
                for (int i = 0; i < targetSequence.length(); i++) {
                    int keyCode = getKeyCode(targetSequence.charAt(i));
                    if (!keySequence.get(keySequence.size() - targetSequence.length() + i).equals(keyCode)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    performAction(entry.getKey(), entry.getValue()); // Передаем и ключ, и значение
                    return; // Выходим после выполнения действия
                }
            }
        }
    }

    private int getKeyCode(char character) {
        switch (Character.toUpperCase(character)) {
            case 'A': return NativeKeyEvent.VC_A;
            case 'B': return NativeKeyEvent.VC_B;
            case 'C': return NativeKeyEvent.VC_C;
            case 'D': return NativeKeyEvent.VC_D;
            case 'E': return NativeKeyEvent.VC_E;
            case 'F': return NativeKeyEvent.VC_F;
            case 'G': return NativeKeyEvent.VC_G;
            case 'H': return NativeKeyEvent.VC_H;
            case 'I': return NativeKeyEvent.VC_I;
            case 'J': return NativeKeyEvent.VC_J;
            case 'K': return NativeKeyEvent.VC_K;
            case 'L': return NativeKeyEvent.VC_L;
            case 'M': return NativeKeyEvent.VC_M;
            case 'N': return NativeKeyEvent.VC_N;
            case 'O': return NativeKeyEvent.VC_O;
            case 'P': return NativeKeyEvent.VC_P;
            case 'Q': return NativeKeyEvent.VC_Q;
            case 'R': return NativeKeyEvent.VC_R;
            case 'S': return NativeKeyEvent.VC_S;
            case 'T': return NativeKeyEvent.VC_T;
            case 'U': return NativeKeyEvent.VC_U;
            case 'V': return NativeKeyEvent.VC_V;
            case 'W': return NativeKeyEvent.VC_W;
            case 'X': return NativeKeyEvent.VC_X;
            case 'Y': return NativeKeyEvent.VC_Y;
            case 'Z': return NativeKeyEvent.VC_Z;
            default: return -1; // Неизвестный символ
        }
    }

    private void performAction(String inputWord, String message) {
        try {
            Robot robot = new Robot();

            // Нажимаем BackSpace столько раз, сколько символов в введенном слове
            for (int i = 0; i < inputWord.length(); i++) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }

            // Копируем сообщение в буфер обмена
            StringSelection stringSelection = new StringSelection(message);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            // Симулируем нажатие клавиш Ctrl + V для вставки текста
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Очищаем последовательность после выполнения действия
            keySequence.clear();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Здесь можно добавить логику для обработки отпускания клавиш, если это необходимо
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Здесь можно добавить логику для обработки набора символов, если это необходимо
    }
}