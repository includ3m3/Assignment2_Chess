import java.util.ArrayList;


public class Knight extends ChessPiece{
	ArrayList<Coordinate> possibleMoves;

	public Knight(int x) {
		super(x);
		
	}

	@Override
	public ArrayList<Coordinate> validMoves(int x) {
		possibleMoves = new ArrayList<Coordinate>();
		int row = x / 8;
		int col = x % 8;
		//Coordinate c = new Coordinate(row, col);
		
		Coordinate[] location = new Coordinate[8];
		location[0] = new Coordinate(row-2,col-1);
		location[1] = new Coordinate(row-1,col-2);
		location[2] = new Coordinate(row+1,col-2);
		location[3] = new Coordinate(row+2,col-1);
		location[4] = new Coordinate(row+2,col+1);
		location[5] = new Coordinate(row+1,col+2);
		location[6] = new Coordinate(row-1,col+2);
		location[7] = new Coordinate(row-2,col+1);
		
		for(Coordinate y:location){
			if(GameEngine.getPieceList().containsKey(y)){
				//possibleMoves.add((8 * y.getROW()) + y.getCOL() + 7);
				//System.out.println(GameEngine.getPieceList().get(location[1]));
				possibleMoves.add(y);
			}
		}
		return possibleMoves;
		
		
	}
	public ArrayList<Coordinate> getPossibleMoves() {
		return possibleMoves;
	}

}
