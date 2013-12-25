import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import javax.swing.JOptionPane;

public class GameEngine {
	private static PlayerColor whitePlayer = PlayerColor.WHITE;
	private static PlayerColor blackPlayer = PlayerColor.BLACK;
	private static PlayerColor currentPlayer = whitePlayer;
	private static Piece[] GameBoard = new Piece[64];
	private static Map<Coordinate, Piece> PieceList;
	private Stack<Coordinate> MovesList;
	private ArrayList<Piece> selectedPieceList;
	private ArrayList<Piece> capturedPieceList;
	private ArrayList<Coordinate> possibleMoves, showList;
	private Piece selectedPiece = null;
	private Pawn pawn;
	private Knight knight;
	private King king;
	private Rook rook;
	private Queen queen;
	private Bishop bishop;
	private int delpanel = 0;
	private boolean moving = false;
	private boolean capture;
	private int from = 0;
	private int to = 0;

	public GameEngine() {
		PieceList = new Hashtable<Coordinate, Piece>();
		possibleMoves = new ArrayList<Coordinate>();
		selectedPieceList = new ArrayList<Piece>();
		capturedPieceList = new ArrayList<Piece>();
		MovesList = new Stack<Coordinate>();
		newGame();
		// for (Map.Entry<Coordinate, Piece> entry : PieceList.entrySet()) {
		// System.out.println(entry.getKey().getROW() + ","
		// + entry.getKey().getCOL() + " has " + entry.getValue());
		// }
		// System.out.println(PieceList.size());

	}

	public void newGame() {
		 System.out.println("Bingo");
		for (int i = 0; i < 64; i++) {
			Coordinate c = new Coordinate(i / 8, i % 8);
			PieceList.put(c, Piece.NONE);
		}

		for (int i = 0; i < 64; i++) {
			GameBoard[i] = Piece.NONE;
			if (i / 8 == 1) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BPAWN;
				PieceList.put(p, Piece.BPAWN);
			}
			if (i / 8 == 6) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.WPAWN;
				PieceList.put(p, Piece.WPAWN);

			}
			if ((i / 8 == 0 && i % 8 == 0) || (i / 8 == 0 && i % 8 == 7)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BROOK;
				PieceList.put(p, Piece.BROOK);
			}
			if ((i / 8 == 7 && i % 8 == 0) || (i / 8 == 7 && i % 8 == 7)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.WROOK;
				PieceList.put(p, Piece.WROOK);
			}
			if ((i / 8 == 0 && i % 8 == 1) || (i / 8 == 0 && i % 8 == 6)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BKNIGHT;
				PieceList.put(p, Piece.BKNIGHT);
			}
			if ((i / 8 == 7 && i % 8 == 1) || (i / 8 == 7 && i % 8 == 6)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.WKNIGHT;
				PieceList.put(p, Piece.WKNIGHT);
			}
			if ((i / 8 == 0 && i % 8 == 2) || (i / 8 == 0 && i % 8 == 5)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BBISHOP;
				PieceList.put(p, Piece.BBISHOP);
			}
			if ((i / 8 == 7 && i % 8 == 2) || (i / 8 == 7 && i % 8 == 5)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.WBISHOP;
				PieceList.put(p, Piece.WBISHOP);
			}
			if ((i / 8 == 0 && i % 8 == 3)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BQUEEN;
				PieceList.put(p, Piece.BQUEEN);
			}
			if ((i / 8 == 7 && i % 8 == 3)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.WQUEEN;
				PieceList.put(p, Piece.WQUEEN);
			}
			if ((i / 8 == 0 && i % 8 == 4)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				GameBoard[i] = Piece.BKING;
				PieceList.put(p, Piece.BKING);
			}
			if ((i / 8 == 7 && i % 8 == 4)) {
				Coordinate p = new Coordinate(i / 8, i % 8);
				// System.out.println(i);
				GameBoard[i] = Piece.WKING;
				PieceList.put(p, Piece.WKING);
			}

		}
	}

	public ArrayList<Coordinate> getPossibleList() {
		return possibleMoves;
	}

	public static void nextTurn() {
		currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer
				: whitePlayer;
	}

	public boolean isMoving() {
		return moving;
	}

	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public PlayerColor currentPlayer() {
		return currentPlayer;
	}

	public Piece[] getBoard() {
		return GameBoard;
	}

	public ArrayList<String> getPos(Piece p) {
		ArrayList<String> pos = new ArrayList<String>();
		for (int i = 0; i < GameBoard.length; i++) {
			if (GameBoard[i] == p) {
				pos.add("(" + i / 8 + "," + i % 8 + ")");
			}
		}
		return pos;
	}

	public static Piece setBoard(int x, Piece i) {
		return GameBoard[x] = i;
	}

	public void move(int x) {
		capture = false;
		selectedPiece = GameBoard[x];
		selectedPieceList.add(selectedPiece);
		// System.out.println(selectedPiece);
		if (moving == false) {
			from = x;
			switch (selectedPiece) {
			case BPAWN:
				if (!isWhiteTurn()) {
					delpanel = x;
					pawn = new Pawn(x);
					pawn.validMoves(x);
					possibleMoves = pawn.getPossibleMoves();
					//System.out.println(pawn.getPossibleMoves());
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case WPAWN:
				if (isWhiteTurn()) {
					delpanel = x;
					pawn = new Pawn(x);
					pawn.validMoves(x);
					possibleMoves = pawn.getPossibleMoves();
					//System.out.println(pawn.getPossibleMoves());
					// possibleMoves.clear();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case BKNIGHT:
				if (!isWhiteTurn()) {
					delpanel = x;
					knight = new Knight(x);
					knight.validMoves(x);
					possibleMoves = knight.getPossibleMoves();
					//System.out.println(knight.getPossibleMoves());
					// possibleMoves.clear();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case WKNIGHT:
				if (isWhiteTurn()) {
					delpanel = x;
					knight = new Knight(x);
					knight.validMoves(x);
					possibleMoves = knight.getPossibleMoves();
					//System.out.println(knight.getPossibleMoves());
					// possibleMoves.clear();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case BKING:
				if (!isWhiteTurn()) {
					delpanel = x;
					king = new King(x);
					king.validMoves(x);
					possibleMoves = king.getPossibleMoves();
					//System.out.println(king.getPossibleMoves());
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case WKING:
				if (isWhiteTurn()) {
					delpanel = x;
					king = new King(x);
					king.validMoves(x);
					possibleMoves = king.getPossibleMoves();
					//System.out.println(king.getPossibleMoves());
					// possibleMoves.clear();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case BROOK:
				if (!isWhiteTurn()) {
					delpanel = x;
					rook = new Rook(x);
					rook.validMoves(x);
					possibleMoves = rook.getPossibleMoves();
					// System.out.println(rook.getPossibleMoves());
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case WROOK:
				if (isWhiteTurn()) {
					delpanel = x;
					rook = new Rook(x);
					rook.validMoves(x);
					possibleMoves = rook.getPossibleMoves();
					// System.out.println(rook.getPossibleMoves());
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case BBISHOP:
				if (!isWhiteTurn()) {
					delpanel = x;
					bishop = new Bishop(x);
					bishop.validMoves(x);
					possibleMoves = bishop.getPossibleMoves();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case WBISHOP:
				if (isWhiteTurn()) {
					delpanel = x;
					bishop = new Bishop(x);
					bishop.validMoves(x);
					possibleMoves = bishop.getPossibleMoves();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case BQUEEN:
				if (!isWhiteTurn()) {
					delpanel = x;
					queen = new Queen(x);
					queen.validMoves(x);
					possibleMoves = queen.getPossibleMoves();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case WQUEEN:
				if (isWhiteTurn()) {
					delpanel = x;
					queen = new Queen(x);
					queen.validMoves(x);
					possibleMoves = queen.getPossibleMoves();
					moving = true;
				} else {
					JOptionPane.showMessageDialog(null, "NOT_YOUR_TURN",
							"Warning", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case NONE:
				JOptionPane.showMessageDialog(null, "EMPTY_SPACE", "Warning",
						JOptionPane.WARNING_MESSAGE);
				moving = false;

				break;
			}
		} else {
			Coordinate c = new Coordinate(x / 8, x % 8);
			GameUI.setText(GameUI.getText());
			if (possibleMoves.contains(c) && !areTheSame(x, delpanel)) {
				if (getPiece(x) != Piece.NONE) {
					capture = true;
					capturedPieceList.add(getPiece(x));
				} else {
					capture = false;
				}
				setBoard(x, selectedPieceList.get(selectedPieceList.size() - 2));
				to = x;
				MovesList.push(new Coordinate(from, to));
				//System.out.println(from + "," + to);
				Coordinate fromPosition = new Coordinate(from / 8, from % 8);
				Coordinate xandy = new Coordinate(x / 8, x % 8);

				GameUI.appendText(isCapture(fromPosition, xandy));

				PieceList.put(xandy,
						selectedPieceList.get(selectedPieceList.size() - 2));
				Coordinate delCoor = new Coordinate(delpanel / 8, delpanel % 8);
				PieceList.put(delCoor, Piece.NONE);
				GameBoard[delpanel] = Piece.NONE;
				moving = false;
				nextTurn();
			} else {
				JOptionPane.showMessageDialog(null, "YOU CAN'T MOVE",
						"Warning", JOptionPane.WARNING_MESSAGE);
				moving = false;
			}

		}
		// System.out.println(selectedPieceList);
	}

	public static Map<Coordinate, Piece> getPieceList() {
		return PieceList;
	}

	public static void setPieceList(Map<Coordinate, Piece> pieceList) {
		PieceList = pieceList;
	}

	public static boolean isWhiteTurn() {
		if (currentPlayer == whitePlayer)
			return true;
		else
			return false;

	}

	public static String getColor(int i) {
		if (GameBoard[i] != Piece.NONE) {
			String p = getPiece(i).toString();
			if (p.substring(0, 1).equalsIgnoreCase("B")) {
				return "BLACK";
			} else {
				return "WHITE";
			}
		}
		return null;
	}

	public static boolean areTheSame(int i, int j) {
		String first = getPiece(i).toString();
		String second = getPiece(j).toString();
		if (first.substring(0, 1).equalsIgnoreCase(second.substring(0, 1))) {
			return true;
		}

		return false;
	}

	public static Piece getPiece(int i) {
		// System.out.println(GameBoard[i].toString());
		return GameBoard[i];
	}

	public String isCapture(Coordinate from, Coordinate to) {
		if (capture) {
			return from + "x" + to + " | ";
		} else {
			return from + "-" + to + " | ";
		}
	}

	public void undo() {

		Coordinate undoCoordinate = MovesList.pop();
		Piece firstPiece = getPiece(undoCoordinate.getCOL());
		//System.out.println(firstPiece);
		Coordinate moveXandY = new Coordinate(undoCoordinate.getCOL() / 8,
				undoCoordinate.getCOL() % 8);
		Coordinate oldXandY = new Coordinate(undoCoordinate.getROW() / 8,
				undoCoordinate.getROW() % 8);

		setBoard(undoCoordinate.getROW(), firstPiece);
		PieceList.put(oldXandY, firstPiece);
		if (capture) {
			PieceList.put(moveXandY,
					capturedPieceList.get(capturedPieceList.size() - 1));
			GameBoard[undoCoordinate.getCOL()] = capturedPieceList
					.get(capturedPieceList.size() - 1);
		} else {
			PieceList.put(moveXandY, Piece.NONE);
			GameBoard[undoCoordinate.getCOL()] = Piece.NONE;
		}
	}

	public ArrayList<Coordinate> showList() {
		showList = new ArrayList<Coordinate>();
		for (int i = 0; i < 64; i++) {
			if (isWhiteTurn()) {
				if (GameBoard[i] == Piece.WPAWN) {		
					showList.add(new Coordinate(i / 8 - 1, i % 8));
					showList.add(new Coordinate(i / 8 - 1, i % 8 - 1));
					showList.add(new Coordinate(i / 8 - 1, i % 8 + 1));
					showList.add(new Coordinate(i / 8, i % 8));
				}
				if (GameBoard[i] == Piece.WROOK) {
					Rook rook1 = new Rook(i);
					rook1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(rook1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.WBISHOP) {
					Bishop bishop1 = new Bishop(i);
					bishop1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(bishop1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.WKING) {
					King king1 = new King(i);
					king1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(king1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.WQUEEN) {
					Queen queen1 = new Queen(i);
					queen1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(queen1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.WKNIGHT) {
					Knight knight1 = new Knight(i);
					knight1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(knight1.getPossibleMoves());
				}
			} else {
				if (GameBoard[i] == Piece.BPAWN) {
					showList.add(new Coordinate(i / 8 + 1, i % 8));
					showList.add(new Coordinate(i / 8 + 1, i % 8 - 1));
					showList.add(new Coordinate(i / 8 + 1, i % 8 + 1));
					showList.add(new Coordinate(i / 8, i % 8));
				}
				if (GameBoard[i] == Piece.BROOK) {
					Rook rook1 = new Rook(i);
					rook1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(rook1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.BBISHOP) {
					Bishop bishop1 = new Bishop(i);
					bishop1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(bishop1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.BKING) {
					King king1 = new King(i);
					king1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(king1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.BQUEEN) {
					Queen queen1 = new Queen(i);
					queen1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(queen1.getPossibleMoves());
				}
				if (GameBoard[i] == Piece.BKNIGHT) {
					Knight knight1 = new Knight(i);
					knight1.validMoves(i);
					showList.add(new Coordinate(i / 8, i % 8));
					showList.addAll(knight1.getPossibleMoves());
				}
			}
		}
		return showList;
	}

	public ArrayList<Coordinate> getshowList() {
		return showList;
	}

	public ArrayList<Piece> getCaptureList() {
		return capturedPieceList;
	}
	public void setCurrentPlayer(PlayerColor x){
		currentPlayer = x;
	}

}
