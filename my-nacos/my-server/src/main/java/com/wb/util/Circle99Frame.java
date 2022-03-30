package com.wb.util;

import javax.swing.*;
import java.awt.*;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/30 13:04
 */
public class Circle99Frame  extends JFrame{
    public static void main(String[] arg) {
        JFrame frame = new Circle99Frame();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("circle 99", 20, 20);
        int x0 = getSize().width / 2;
        int y0 = getSize().height / 2;
        for (int r = 0; r < getSize().height / 2; r += 10) {
            g.setColor(getRandomColor());
            g.drawOval(x0 - r, y0 - r, r * 2, r * 2);
        }
    }

    Color getRandomColor() {
        return new Color(
                (int) (Math.random() * 255),//random本身只产生（0~1）之间的小数，
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)
        );
    }


}
