public class Sun {
    private int columnPos;
    private int rowPos;
    private int value;
    private boolean isActive;
    private int lifespan;

    public Sun(){
        isActive = true;
        lifespan = 10;
        value = 50;
    }

    public Sun(int rowPos, int columnPos) {
        this();
        this.rowPos = rowPos;
        this.columnPos = columnPos;
    }

    public Sun(int rowPos, int columnPos, int value) {
        this();
        this.rowPos = rowPos;
        this.columnPos = columnPos;
        this.value = value;
    }

    public int getColumnPos(){ return columnPos; }
    public int getRowPos(){ return rowPos; }
    public int getValue(){ return value; }
    public boolean isActive(){ return isActive; }
    
    public void collected() { isActive = false; }
    public void updateSun(){
        if (lifespan > 0){
            lifespan--;
        }
        else {
            isActive = false;
        }
    }
}
