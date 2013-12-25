import java.util.ArrayList;

public abstract class ChessPiece {
	protected int x;

	public ChessPiece(int x) {
		this.x = x;
		
	}
	
	public abstract ArrayList<Coordinate> validMoves(int x);
	public abstract ArrayList<Coordinate> getPossibleMoves();
}