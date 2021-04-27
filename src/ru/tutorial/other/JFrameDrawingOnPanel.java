package ru.tutorial.other;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Graphics;
import java.util.Random;

public class JFrameDrawingOnPanel {

    public static void main(String[] args) {
        Random r = new Random();
        JFrame frame = new JFrame();
        frame.setTitle("Some title");
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(650, 600);

        frame.setLayout(null);

        DrawingArea drawingArea = new DrawingArea();
        drawingArea.setBounds(0, 0, 400, 400);
        frame.add(drawingArea);


        //Если нужна постоянная перерисовка
//        Timer repaintFrame = new Timer(1000 / 40, e -> drawingArea.repaint());
//        repaintFrame.start();


        JSlider slider = new JSlider(0, 400);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(100);
        slider.setBounds(100, 450, 200, 50);

        slider.addChangeListener(e -> {
            drawingArea.size = slider.getValue();
            drawingArea.repaint();
        });
        frame.add(slider);

        JButton button = new JButton("RANDOM COLOR");
        button.setBounds(100, 500, 200, 50);
        button.addActionListener(l -> {
            drawingArea.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            drawingArea.repaint();
        });
        frame.add(button);


        frame.setVisible(true);
    }
}

class DrawingArea extends JPanel {
    Color color = new Color(0, 0, 0);
    int size = 100;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(50, 50, size, size);
    }
}