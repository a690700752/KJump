package com.werfad;

import javax.swing.*;
import java.awt.*;

public class MarksComponent extends JComponent {

    @Override
    public void paint(Graphics g) {
        g.drawString("Hello world", 400, 300);
        super.paint(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello world");
        frame.setSize(800, 600);
        frame.add(new MarksComponent());
        frame.setVisible(true);
    }

}
