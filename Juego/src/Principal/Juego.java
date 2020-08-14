package Principal;

import javax.swing.JFrame;

public class Juego {
	
public static void main(String[] args) {
		
		JFrame window = new JFrame("Fighters");
		window.setContentPane(new PanelJuego());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
