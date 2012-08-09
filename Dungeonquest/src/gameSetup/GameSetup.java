package gameSetup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GameSetup extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5406843248645796709L;

	JRadioButton onePlayerGame = new JRadioButton();
	JRadioButton twoPlayerGame = new JRadioButton();
	JRadioButton threePlayerGame = new JRadioButton();
	JRadioButton fourPlayerGame = new JRadioButton();

	ButtonGroup playerSelection = new ButtonGroup();

	private final String onePlayerString = "One Player";
	private final String twoPlayerString = "Two Players";
	private final String threePlayerString = "Three Players";
	private final String fourPlayerString = "Four Players";

	private GameSetup() {
		super();

		onePlayerGame.setSelected(true);

		playerSelection.add(onePlayerGame);
		onePlayerGame.setMnemonic(KeyEvent.VK_1);
		onePlayerGame.setActionCommand(onePlayerString);

		playerSelection.add(twoPlayerGame);
		onePlayerGame.setMnemonic(KeyEvent.VK_2);
		onePlayerGame.setActionCommand(twoPlayerString);

		playerSelection.add(threePlayerGame);
		onePlayerGame.setMnemonic(KeyEvent.VK_3);
		onePlayerGame.setActionCommand(threePlayerString);

		playerSelection.add(fourPlayerGame);
		onePlayerGame.setMnemonic(KeyEvent.VK_4);
		onePlayerGame.setActionCommand(fourPlayerString);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case onePlayerString:
			break;
		case twoPlayerString:
			break;
		case threePlayerString:
			break;
		case fourPlayerString:
			break;
		}
	}

}
