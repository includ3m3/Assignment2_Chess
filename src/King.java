import java.util.ArrayList;

public class King extends ChessPiece {
	ArrayList<Coordinate> possibleMoves;

	public King(int x) {
		super(x);

	}

	@Override
	public ArrayList<Coordinate> getPossibleMoves() {
		possibleMoves = new ArrayList<Coordinate>();
		int row = x / 8;
		int col = x % 8;

		Coordinate[] location = new Coordinate[8];

		location[0] = new Coordinate(row - 1, col - 1);
		location[1] = new Coordinate(row, col - 1);
		location[2] = new Coordinate(row + 1, col - 1);
		location[3] = new Coordinate(row - 1, col);
		location[4] = new Coordinate(row + 1, col);
		location[5] = new Coordinate(row + 1, col + 1);
		location[6] = new Coordinate(row, col + 1);
		location[7] = new Coordinate(row - 1, col + 1);

		for (Coordinate c : location) {
			if (GameEngine.getPieceList().containsKey(c)){
				//System.out.println(c);
				possibleMoves.add(c);
			}
		}
		return possibleMoves;
	}

	public ArrayList<Coordinate> validMoves(int x) {

		return possibleMoves;
	}
}
