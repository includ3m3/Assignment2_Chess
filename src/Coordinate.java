
public class Coordinate {
	int ROW,COL;
	public Coordinate(int row,int col){
		this.ROW= row;
		this.COL= col;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + COL;
		result = prime * result + ROW;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (COL != other.COL)
			return false;
		if (ROW != other.ROW)
			return false;
		return true;
	}
	public int getROW() {
		return ROW;
	}
	public void setROW(int row) {
		ROW = row;
	}
	public int getCOL() {
		return COL;
	}
	public void setCOL(int col) {
		COL = col;
	}
	
	public String toString()
    {
    	int col = getCOL();
    	int row = getROW();
    	char pngCol;
    	int pngRow;
    	switch (col)
    	{
    		case 0:
    			pngCol = 'a'; break;
    		case 1:
    			pngCol = 'b'; break;
    		case 2:
    			pngCol = 'c'; break;
    		case 3:
    			pngCol = 'd'; break;
    		case 4:
    			pngCol = 'e'; break;
    		case 5:
    			pngCol = 'f'; break;
    		case 6:
    			pngCol = 'g'; break;
    		case 7:
    			pngCol = 'h'; break;
    		default:
    			pngCol = ' '; break;
    	}
    	
    	switch (row)
    	{
    		case 0:
    			pngRow = 8; break;
    		case 1:
    			pngRow = 7; break;
    		case 2:
    			pngRow = 6; break;
    		case 3:
    			pngRow = 5; break;
    		case 4:
    			pngRow = 4; break;
    		case 5:
    			pngRow = 3; break;
    		case 6:
    			pngRow = 2; break;
    		case 7:
    			pngRow = 1; break;
    		default:
    			pngRow = 0; break;
    	}
    	
    	return ""+pngCol+""+pngRow;
    }

}
