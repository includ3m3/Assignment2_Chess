import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class GameUI {
	public static final int NUMBER_OF_ROWS = 8;
	public static final int NUMBER_OF_COLS = 8;
	private JFrame frame;
	public static JLabel location, info, statusInfo;
	public static JPanel topPanel, centralPanel, bottomPanel;
	public static JTextArea textArea;
	private JLabel B, BB, BN, BR, BQ, BK, W, WB, WN, WR, WQ, WK;
	private JLabel[] Jlabellist = { B, BB, BN, BR, BQ, BK, W, WB, WN, WR, WQ,
			WK };
	private JPanel slotPanel = new JPanel(new GridLayout(8, 8));
	private static JPanel[] Board = new JPanel[64];
	GameEngine ge;
	String[] string_list = { "B", "BB", "BN", "BR", "BQ", "BK", "W", "WB",
			"WN", "WR", "WQ", "WK" };
	int originalP = 0;
	static String allText = "";

	public GameUI() {

		frame = new JFrame("CHESS");
		frame.setResizable(false);
		frame.setBounds(200, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		JMenu gameMenu = new JMenu("Menu");
		gameMenu.setBackground(Color.LIGHT_GRAY);
		menuBar.add(gameMenu);

		JMenuItem newGameItem = new JMenuItem("Start new game");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem exit = new JMenuItem("Exit");
		gameMenu.add(newGameItem);
		gameMenu.add(undo);
		gameMenu.add(exit);
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				start();

			}
		});
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ge.undo();
				textArea.setText(null);
				textArea.setText(allText);
				GameEngine.nextTurn();
				paint();
				slotPanel.updateUI();
				updateStatus();
				
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});
		frame.setJMenuBar(menuBar);

		// top Panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 1));
		JPanel topPanel_sub1 = new JPanel();
		JPanel topPanel_sub2 = new JPanel();
		info = new JLabel("Info Panel");
		location = new JLabel("Location");
		Font font = new Font("Verdana", Font.BOLD, 12);

		info.setFont(font);
		location.setFont(font);
		info.setForeground(Color.RED);
		topPanel_sub1.add(info);
		topPanel_sub1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topPanel_sub2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topPanel_sub2.add(location);

		topPanel.setBorder(new TitledBorder("Status"));
		topPanel.add(topPanel_sub1);
		topPanel.add(topPanel_sub2);
		// END of TOP
		// Central Panel
		centralPanel = new JPanel();
		TitledBorder border = new TitledBorder("Display");
		centralPanel.setBorder(border);
		JPanel central_sub2 = new JPanel();

		textArea = new JTextArea(25, 15);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		central_sub2.add(scrollPane);
		centralPanel.add(createDisplay(), BorderLayout.EAST);
		centralPanel.add(central_sub2, BorderLayout.WEST);
		// END OF CENTER

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(centralPanel, BorderLayout.CENTER);
		frame.add(msgbox(), BorderLayout.SOUTH);
		// CreateBoard1();
		frame.setVisible(true);
	}

	public JPanel msgbox() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 6));
		TitledBorder border = new TitledBorder("Msg Box");
		bottomPanel.setBorder(border);
		for (int i = 0; i < Jlabellist.length; i++) {
			Jlabellist[i] = new JLabel(string_list[i] + ": 0");
			bottomPanel.add(Jlabellist[i]);
		}

		return bottomPanel;
	}

	public JPanel createDisplay() {
		JPanel central_sub1 = new JPanel();
		for (int i = 0; i < NUMBER_OF_ROWS * NUMBER_OF_COLS; i++) {
			final JPanel button = new JPanel();
			button.setToolTipText("location " + i);
			button.setOpaque(true);
			button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			button.setBackground(new Color(192, 192, 192));

			if ((i / NUMBER_OF_ROWS + i % NUMBER_OF_COLS) % 2 == 1) {
				button.setBackground(new Color(112, 58, 0));
			}

			final int j = i;
			button.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// ge.hideList();
					for (int i = 0; i < 64; i++) {
						Board[i].setBackground(new Color(192, 192, 192));

						if ((i / NUMBER_OF_ROWS + i % NUMBER_OF_COLS) % 2 == 1) {
							Board[i].setBackground(new Color(112, 58, 0));
						}
					}
					originalP = j;
					ge.move(j);
					slotPanel.updateUI();
					paint();
					updateStatus();
					checkWin();

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					try {
						if (!ge.isMoving()) {
							button.setBackground(new Color(163, 170, 170));
						}
					} catch (NullPointerException e) {
						info.setText("Please start the game!");
					}
					int x = j / 8;
					int y = j % 8;
					Coordinate dynamicStatus = new Coordinate(x, y);
					location.setText("" + dynamicStatus);

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					try {
						if (!ge.isMoving()) {
							button.setBackground(new Color(192, 192, 192));
							if ((j / NUMBER_OF_ROWS + j % NUMBER_OF_COLS) % 2 == 1) {
								button.setBackground(new Color(112, 58, 0));
							}
						}
					} catch (NullPointerException e) {
						info.setText("Please start the game!");
					}

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

			});
			slotPanel.add(button);
			slotPanel.setPreferredSize(new Dimension(700, 420));
			Board[i] = button;
			central_sub1.add(slotPanel);
		}
		return central_sub1;
	}

	public void paint() {
		ge.showList();
		ArrayList<Coordinate> showList = ge.getshowList();
		//System.out.println(showList);
		for (int i = 0; i < ge.getBoard().length; i++) {
			Board[i].removeAll();
			// images for white player
			if (showList.contains(new Coordinate(i / 8, i % 8))) {
				if (ge.getBoard()[i] == Piece.WPAWN) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WPawn.png")));
				}
				if (ge.getBoard()[i] == Piece.WKNIGHT) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WKnight.png")));
				}
				if (ge.getBoard()[i] == Piece.WBISHOP) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WBishop.png")));
				}

				if (ge.getBoard()[i] == Piece.WROOK) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WRook.png")));
				}
				if (ge.getBoard()[i] == Piece.WQUEEN) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WQueen.png")));
				}
				if (ge.getBoard()[i] == Piece.WKING) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/WKing.png")));
				}

				// images for black player
				if (ge.getBoard()[i] == Piece.BPAWN) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BPawn.png")));
				}

				if (ge.getBoard()[i] == Piece.BKNIGHT) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BKnight.png")));
				}
				if (ge.getBoard()[i] == Piece.BROOK) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BRook.png")));
				}

				if (ge.getBoard()[i] == Piece.BBISHOP) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BBishop.png")));
				}

				if (ge.getBoard()[i] == Piece.BQUEEN) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BQueen.png")));
				}

				if (ge.getBoard()[i] == Piece.BKING) {
					Board[i].removeAll();
					Board[i].add(new JLabel(new ImageIcon("img/BKing.png")));
				}
			} else {
				Board[i].removeAll();
				JLabel questionMark = new JLabel("?");
				questionMark.setForeground(Color.BLUE);
				Board[i].add(questionMark);
			}

		}

		if (!ge.getPossibleList().isEmpty() && ge.isMoving()) {
			for (Coordinate j : ge.getPossibleList()) {
				int panelInt = j.getROW() * 8 + j.getCOL();
				if (GameEngine.getPieceList().get(j) == Piece.NONE) {
					Board[panelInt].setBackground(Color.RED);
				} else if (GameEngine.getPieceList().get(j) != Piece.NONE
						&& !GameEngine.areTheSame(originalP, panelInt)) {
					Board[panelInt].setBackground(Color.YELLOW);
				}
			}
		}

	}

	public void start() {
		ge = new GameEngine();
		ge.showList();
		info.setText("WHITE TURN");
		paint();
		slotPanel.updateUI();
		for(int i=0;i<Jlabellist.length;i++){
			Jlabellist[i].setText(string_list[i] + ": 0");
		}

	}

	public void updateStatus() {
		ArrayList<Piece> captureList = ge.getCaptureList();
		int Bcounter = 0, BBcounter = 0, BNcounter = 0, BRcounter = 0, BQcounter = 0, BKcounter = 0, Wcounter = 0, WBcounter = 0, WNcounter = 0, WRcounter = 0, WQcounter = 0, WKcounter = 0;
		for (int i = 0; i < captureList.size(); i++) {
			// score for black player
			if (captureList.get(i) == Piece.BPAWN) {
				Bcounter++;
				Jlabellist[0].setText(string_list[0] + ": " + Bcounter);
			}
			if (captureList.get(i) == Piece.BBISHOP) {
				BBcounter++;
				Jlabellist[1].setText(string_list[1] + ": " + BBcounter);
			}
			if (captureList.get(i) == Piece.BKNIGHT) {
				BNcounter++;
				Jlabellist[2].setText(string_list[2] + ": " + BNcounter);
			}
			if (captureList.get(i) == Piece.BROOK) {
				BRcounter++;
				Jlabellist[3].setText(string_list[3] + ": " + BRcounter);
			}
			if (captureList.get(i) == Piece.BQUEEN) {
				BQcounter++;
				Jlabellist[4].setText(string_list[4] + ": " + BQcounter);
			}
			if (captureList.get(i) == Piece.BKING) {
				BKcounter++;
				Jlabellist[5].setText(string_list[5] + ": " + BKcounter);
			}

			// score for white player
			if (captureList.get(i) == Piece.WPAWN) {
				Wcounter++;
				Jlabellist[6].setText(string_list[6] + ": " + Wcounter);
			}
			if (captureList.get(i) == Piece.WBISHOP) {
				WBcounter++;
				Jlabellist[7].setText(string_list[7] + ": " + WBcounter);
			}
			if (captureList.get(i) == Piece.WKNIGHT) {
				WNcounter++;
				Jlabellist[8].setText(string_list[8] + ": " + WNcounter);
			}
			if (captureList.get(i) == Piece.WROOK) {
				WRcounter++;
				Jlabellist[9].setText(string_list[9] + ": " + WRcounter);
			}
			if (captureList.get(i) == Piece.WQUEEN) {
				WQcounter++;
				Jlabellist[10].setText(string_list[10] + ": " + WQcounter);
			}
			if (captureList.get(i) == Piece.WKING) {
				WKcounter++;
				Jlabellist[11].setText(string_list[11] + ": " + WKcounter);
			}

		}
		if (GameEngine.isWhiteTurn()) {
			info.setText("WHITE TURN");
		} else {
			info.setText("BLACK TURN");
		}

	}

	public static String getLocationString() {
		return location.getText();
	}

	public static void appendText(String s) {
		textArea.append(s);

	}
	public static void setText(String s){
		allText = s;
	}
	public static String getText(){
		return textArea.getText();
	}

	public void checkWin() {
		if (ge.getCaptureList().contains(Piece.BKING)) {
			int n = JOptionPane
					.showConfirmDialog(
							frame,
							"WHITE player has won the Game! Would you like to restart the game?",
							"Restart or Exit", JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				ge.setCurrentPlayer(PlayerColor.WHITE);
				textArea.setText(null);
				start();
			} else {
				System.exit(0);
			}

		}
		if (ge.getCaptureList().contains(Piece.WKING)) {
			int n = JOptionPane
					.showConfirmDialog(
							frame,
							"BLACK player has won the Game! Would you like to restart the game?",
							"Restart or Exit", JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				ge.setCurrentPlayer(PlayerColor.WHITE);
				textArea.setText(null);
				start();
			} else {
				System.exit(0);
			}

		}

	}

}
