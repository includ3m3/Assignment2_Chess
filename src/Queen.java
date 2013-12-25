import java.util.ArrayList;


public class Queen extends ChessPiece{
	ArrayList<Coordinate> possibleMoves;

	public Queen(int x) {
		super(x);
		
	}

	@Override
	public ArrayList<Coordinate> validMoves(int x) {
		possibleMoves = new ArrayList<Coordinate>();
		
		int row = x / 8;
		int col = x % 8;

		Coordinate[] location = new Coordinate[64];
		boolean[] isRowBlocked = new boolean[8];

		for(int i=1; i<=64; i++)
		{
			if(i<=8)
				location[i-1] = new Coordinate( row-i%8,col-i%8);
			else if(i<=16)
				location[i-1] = new Coordinate(row,col-i%8);
			else if(i<=24)
				location[i-1] = new Coordinate(row+i%8,col-i%8);
			else if(i<=32)
				location[i-1] = new Coordinate(row+i%8,col);
			else if(i<=40)
				location[i-1] = new Coordinate(row+i%8,col+i%8);
			else if(i<=48)
				location[i-1] = new Coordinate(row,col+i%8);
			else if(i<=56)
				location[i-1] = new Coordinate(row-i%8,col+i%8);
			else
				location[i-1] = new Coordinate( row-i%8,col);
		}
		for (int i = 0; i < location.length; i++) {
			if (GameEngine.getPieceList().containsKey(location[i])) {
				int j = (location[i].getROW() * 8) + location[i].getCOL();
				//System.out.println(x+","+j);
				if (!isRowBlocked[i / 8] && !GameEngine.areTheSame(x, j)) {
					possibleMoves.add(location[i]);
					if (GameEngine.getPieceList().get(location[i]) != Piece.NONE) {
						isRowBlocked[i / 8] = true;
					}
				} else {
					isRowBlocked[i / 8] = true;
				}
			}
		}

		return possibleMoves;
	}

	@Override
	public ArrayList<Coordinate> getPossibleMoves() {
		// TODO Auto-generated method stub
		return possibleMoves;
	}

}
