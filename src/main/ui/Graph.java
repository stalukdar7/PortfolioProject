package ui;

import javax.swing.*;
import java.awt.*;

public class Graph extends JPanel {
    int[] data;
    final int scale = 20;
/*
    {
        data = new int[]{25, 60, 42, 75};
    }

 */





    public Graph(int[] i) {
        data = i;
    }



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        g2.drawLine(scale, scale, scale, h - scale);
        g2.drawLine(scale, h - scale, w - scale, h - scale);
        double xscale = (w - 2 * scale) / (data.length + 1);
        double maxValue = 100.0;
        double yscale = (h - 2 * scale) / maxValue;
        // The origin location.
        int x0 = scale;
        int y0 = h - scale;
        g2.setPaint(Color.red);
        for (int j = 0; j < data.length; j++) {
            int x = x0 + (int) (xscale * (j + 1));
            int y = y0 - (int) (yscale * data[j]);
            g2.fillOval(x - 2, y - 2, 4, 4);
        }
    }
}
