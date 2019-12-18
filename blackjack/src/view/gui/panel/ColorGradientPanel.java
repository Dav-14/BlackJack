package view.gui.panel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ColorGradientPanel extends JPanel{

    private Color color1;
    private Color color2;

    public static final Color DARKGREEN  = new Color(13, 77, 47);

    public ColorGradientPanel(Color color1, Color color2)
    {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, this.color1, 0, h, this.color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        Color color1 = Color.BLACK;
        Color color2 = new Color(13, 77, 47);
        JPanel cgp = new ColorGradientPanel(color1, color2);

        frame.add(cgp);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}