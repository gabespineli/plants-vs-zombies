public class Dormitory {
    public Dormitory(String n, int r, int m){
        int i;
        name = n;
        numOfRooms = r;
        rooms = new Room[r];
        for (i = 0; i < r; i++){
            rooms[i] = new Room(i+1, m);
        }
    }

    public Dormitory(String n, int r){
        this(n, r, 6);
    }

    public boolean acceptGuests(Person guest, int roomNum){
        if (roomNum < numOfRooms && roomNum > 0 && !(rooms[roomNum-1].isFull())){
            return rooms[roomNum-1].addGuest(guest);
        }
        return false;
    }

    public Person[] getGuests(int roomNum){
        if (roomNum < numOfRooms && roomNum > 0){
            return rooms[roomNum-1].getGuests();
        }
        return null;
    }

    public String getName(){
        return name;
    }
    public Room[] getRooms(){
        return rooms;
    }
    public int getNumOfRooms(){ return numOfRooms; }

    private String name;
    private Room[] rooms;
    private int numOfRooms;
}
