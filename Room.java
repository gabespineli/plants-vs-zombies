public class Room {
    public Room(int roomNum, int maxCapacity)
    {
        NUM = roomNum;
        MAX = maxCapacity;
        size = 0;
        guests = new Person[MAX];
    }
    public boolean addGuest(Person guest)
    {
        for (int i = 0; i < MAX; i++) {
            if (guests[i] == null) {
                guests[i] = guest;
                size++;
                return true;
            }
        }
        return false;
    }
    public boolean isFull()
    {
        return size == getMaxCapacity();
    }
    public boolean isEmpty()
    {
        return size == 0;
    }
    public int getMaxCapacity()
    {
        return MAX;
    }
    public int getRoomNum()
    {
        return NUM;
    }
    public Person[] getGuests()
    {
        return guests;
    }
    public int getNumOccupants()
    {
        return size;
    }
    public void removeGuest(int index) {
        if (index >= 0 || index < size) {
            for (int i=index; i < guests.length-1; i++) {
                guests[i] = guests[i + 1];
            }
            guests[guests.length-1] = null;
            size--;
        }
    }

    private final int MAX; // max number of guests
    private final int NUM; // room number
    private Person[] guests; // do not change to ArrayList
    private int size; // current number of guests in room a single room
}
