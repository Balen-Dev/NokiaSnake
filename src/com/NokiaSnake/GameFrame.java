package com.NokiaSnake;

import javax.swing.*;

public class GameFrame extends JFrame {

    ImageIcon icon = new ImageIcon("snake.png");

    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
