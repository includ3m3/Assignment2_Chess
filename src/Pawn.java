import java.util.ArrayList;

public class Pawn extends ChessPiece {
	ArrayList<Coordinate> possibleMoves;

	public Pawn(int x) {
		super(x);

	}

	public ArrayList<Coordinate> getPossibleMoves() {
		return possibleMoves;
	}

	public ArrayList<Coordinate> validMoves(int x) {
		possibleMoves = new ArrayList<Coordinate>();
		int row = x / 8;
		int col = x % 8;
		Coordinate c = new Coordinate(row, col);
		// System.out.println(GameEngine.getPieceList().get(c));

		Coordinate blackOne = new Coordinate(row + 1, col);
		Coordinate blackTwo = new Coordinate(row + 2, col);
		Coordinate blackLeft = new Coordinate(row + 1, col - 1);
		Coordinate blackRight = new Coordinate(row + 1, col + 1);

		Coordinate whiteOne = new Coordinate(row - 1, col);
		Coordinate whiteTwo = new Coordinate(row - 2, col);
		Coordinate whiteLeft = new Coordinate(row - 1, col - 1);
		Coordinate whiteRight = new Coordinate(row - 1, col + 1);

		if (!GameEngine.isWhiteTurn()) {
			if (c.ROW == 1) {
				if (GameEngine.getPieceList().get(blackTwo) == Piece.NONE) {
					possibleMoves.add(blackTwo);
					possibleMoves.add(blackOne);
				}else if (GameEngine.getPieceList().get(blackOne) == Piece.NONE) {
					possibleMoves.add(blackOne);

				}
			} else {
				if (GameEngine.getPieceList().get(blackOne) == Piece.NONE) {
					possibleMoves.add(blackOne);
				}
			}
			
			if (GameEngine.getPieceList().get(blackLeft) != Piece.NONE) {
				possibleMoves.add(blackLeft);
			}
			if (GameEngine.getPieceList().get(blackRight) != Piece.NONE) {
				possibleMoves.add(blackRight);
			}
		} else {
			if (c.ROW == 6) {
				if (GameEngine.getPieceList().get(whiteTwo) == Piece.NONE) {
					possibleMoves.add(whiteTwo);
					possibleMoves.add(whiteOne);
				}else if (GameEngine.getPieceList().get(whiteOne) == Piece.NONE) {
					possibleMoves.add(whiteOne);

				}
			} else {
				if (GameEngine.getPieceList().get(whiteOne) == Piece.NONE) {
					possibleMoves.add(whiteOne);
				}
			}
			
			if (GameEngine.getPieceList().get(whiteLeft) != Piece.NONE) {
				possibleMoves.add(whiteLeft);
			}
			if (GameEngine.getPieceList().get(whiteRight) != Piece.NONE) {
				possibleMoves.add(whiteRight);
			}
		}
		return possibleMoves;
	}

}
