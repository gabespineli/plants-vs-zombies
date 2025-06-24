package Program;

public class Sun {
    private int columnPos;
    private int rowPos;
    private boolean isActive;
    private int lifeSpan;

    public Sun(){
        isActive = true;
        lifeSpan = 10;
    }

    public Sun(int rowPos, int columnPos) {
        this.rowPos = rowPos;
        this.columnPos = columnPos;
        isActive = true;
        lifeSpan = 10;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public void setColumnPos(int columnPos){
        this.columnPos =  columnPos;
    }

    public int getColumnPos(){
        return columnPos;
    }

    public int getRowPos(){
        return rowPos;
    }

    public void update(){
        if (lifeSpan > 0){
            lifeSpan--;
        }
        else {
            isActive = false;
        }
    }
}
