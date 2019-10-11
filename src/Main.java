import javax.swing.*;
import java.awt.*;

public class Main {
    private static Frame f=new Frame();
    public static void main(String[] args) {
        JFrame window=new JFrame("Generator");
        //window.setMenuBar(generateMenus());
        window.setVisible(true);
        window.add(f);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
    }

    //Generates menu options
    private static MenuBar generateMenus(){
        return null;
    }
}
