public class Dormitory {
    private String name;
    private Room[] rooms;
    private int numOfRooms;

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
        int i;
        name = n;
        numOfRooms = r;
        rooms = new Room[r];
        for (i = 0; i < r; i++){
            rooms[i] = new Room(i+1, 6);
        }
    }

    public boolean acceptGuests(int roomNum){
        if (roomNum < numOfRooms && roomNum > 0){
            if (!(rooms[roomNum-1].isFull()))
                return true;
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
}
