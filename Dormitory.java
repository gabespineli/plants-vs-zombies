public class Dormitory {
    public Dormitory(String n, int r, int m){
        int i;
        NAME = n;
        NUM_OF_ROOMS = r;
        rooms = new Room[r];
        for (i = 0; i < r; i++){
            rooms[i] = new Room(i+1, m);
        }
    }

    public Dormitory(String n, int r){
        this(n, r, 6);
    }

    public boolean acceptGuests(Person guest, int roomNum){
        if (roomNum < NUM_OF_ROOMS && roomNum > 0 && !(rooms[roomNum-1].isFull())){
            return rooms[roomNum-1].addGuest(guest);
        }
        return false;
    }

    public Person[] getGuests(int roomNum){
        if (roomNum < NUM_OF_ROOMS && roomNum > 0){
            return rooms[roomNum-1].getGuests();
        }
        return null;
    }

    public String getName(){
        return NAME;
    }
    public Room[] getRooms(){
        return rooms;
    }
    public int getNumOfRooms(){ return NUM_OF_ROOMS; }

    private final String NAME;
    private Room[] rooms;
    private final int NUM_OF_ROOMS;
}