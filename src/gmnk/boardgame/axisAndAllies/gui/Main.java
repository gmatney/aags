package gmnk.boardgame.axisAndAllies.gui;

import javax.swing.JApplet;

public class Main extends JApplet {
    GameBoardGUI game;
    
    public void init() {
        game = new GameBoardGUI();
        add(game);
        setSize(800,600);
     }
}
