import java.util.*;
public class DormDriver
{
    public void display(Room room)
    {
        /* display all the guests in the room */
        int i;
        for (i = 0; i < room.getNumOccupants(); i++){
            System.out.println("\t" + room.getGuests()[i].toString());
        }
    }

    public void displayDorms(Dormitory[] dorms)
    {
        int i, j, roomsAvailable = 0;
        for (i = 0; i < dorms.length; i++)
        {
			/* Display the name of the dorm, the total
			   number of rooms, and the number of rooms
			   that are not full yet */
            System.out.println("Name of Dormitory: " + dorms[i].getName());
            System.out.println("Number of rooms: " + dorms[i].getNumOfRooms());
            for (j = 0; j < dorms[i].getNumOfRooms(); j++){
                if (!(dorms[i].getRooms()[j].isFull())) {
                    roomsAvailable++;
                }
            }
            System.out.println("Number of rooms available: " + roomsAvailable);

			/* Display all the names and nationalities
			   of the guests in each room. Part of the
			   solution is to call the method display()
			   in DormDriver. Provide your code */
            for (j = 0; j < dorms[i].getNumOfRooms(); j++){
                if (!(dorms[i].getRooms()[j].isEmpty())) {
                    System.out.println("Room #" + dorms[i].getRooms()[j].getRoomNum() + " guests: ");
                    display(dorms[i].getRooms()[j]);
                }
            }
            System.out.println("------------------------------");
        }

    }

    public static void main(String[] args)
    {
        Dormitory[] dorms = new Dormitory[2];

        dorms[0] = new Dormitory("LS Dorm", 3);
        dorms[1] = new Dormitory("STC Dorm", 5, 4);

        ArrayList<Person> guests = new ArrayList<>();
        guests.add(new Person("Andrew", "Filipino"));
        guests.add(new Person("Miguel", "Filipino"));
        guests.add(new Person("Henry", "American"));
        guests.add(new Person("Ray", "Filipino"));
        guests.add(new Person("Bernie", "Filipino"));
        guests.add(new Person("Michael", "Singaporean"));
        guests.add(new Person("Victor", "Filipino"));
        guests.add(new Person("Dennis", "Filipino"));
        guests.add(new Person("Jaime", "Filipino"));

		/* Have all Filipinos be in the same room, as
		   long as they fit.  Following first come, first
		   served, those who do not fit will be assigned
		   to the next room. Use the first dormitory for
		   the Filipinos.  For the other nationalities,
		   they will be assigned to the second dormitory in
		   separate rooms. Provide your code. */
        int i, roomNum = 1;
        for (i = 0; i < guests.size(); i++) {
            if (guests.get(i).getNationality().equalsIgnoreCase("Filipino")) {
                while (!dorms[0].acceptGuests(guests.get(i), roomNum)
                        && roomNum < dorms[0].getNumOfRooms()) {
                    roomNum++;
                }
            }
        }
        roomNum = 1;
        for (i = 0; i < guests.size(); i++) {
            if (!guests.get(i).getNationality().equalsIgnoreCase("Filipino")) {
                while (roomNum <= dorms[1].getNumOfRooms()
                        && (!dorms[1].getRooms()[roomNum-1].isEmpty()
                        || !dorms[1].acceptGuests(guests.get(i), roomNum))) {
                    roomNum++;
                }
            }
        }

		/* Provide your code to call displayDorms() in
		   class DormDriver. */
        DormDriver driver = new DormDriver();
        driver.displayDorms(dorms);

		/* Provide code to transfer Ray to STC Dorm, and
			he wants to be assigned to a currently unoccupied
			room. */
        System.out.println("\n**Transferring Ray**");
        int j, k;
        for (i = 0; i < dorms.length; i++){
            for (j = 0; j < dorms[i].getNumOfRooms(); j++){
                for (k = 0; k < dorms[i].getRooms()[j].getGuests().length; k++){
                    if (dorms[i].getRooms()[j].getGuests()[k] != null){
                        if (dorms[i].getRooms()[j].getGuests()[k].getName().equalsIgnoreCase("Ray")){
                            dorms[i].getRooms()[j].removeGuest(k);
                            break;
                        }
                    }
                }
            }
        }

        for (i = 0; i < dorms.length; i++) {
            for (j = 0; j < dorms[i].getNumOfRooms(); j++) {
                if (dorms[i].getName().equalsIgnoreCase("STC Dorm")){
                    if (dorms[i].getRooms()[j].isEmpty()) {
                        dorms[i].getRooms()[j].addGuest(guests.get(3));
                        break;
                    }
                }

            }
        }


		/* Provide code to transfer Michael to the same room
		   as Miguel */
        System.out.println("\n**Transferring Michael**\n");
        for (i = 0; i < dorms.length; i++){
            for (j = 0; j < dorms[i].getNumOfRooms(); j++){
                for (k = 0; k < dorms[i].getRooms()[j].getGuests().length; k++){
                    if (dorms[i].getRooms()[j].getGuests()[k] != null){
                        if (dorms[i].getRooms()[j].getGuests()[k].getName().equalsIgnoreCase("Michael")){
                            dorms[i].getRooms()[j].removeGuest(k);
                            break;
                        }
                    }
                }
            }
        }

        for (i = 0; i < dorms.length; i++){
            for (j = 0; j < dorms[i].getNumOfRooms(); j++){
                for (k = 0; k < dorms[i].getRooms()[j].getGuests().length; k++){
                    if (dorms[i].getRooms()[j].getGuests()[k] != null){
                        if (dorms[i].getRooms()[j].getGuests()[k].getName().equalsIgnoreCase("Miguel")){
                            dorms[i].getRooms()[j].addGuest(guests.get(5));
                            break;
                        }
                    }
                }
            }
        }

		/* Provide your code to call displayDorms() in
		   class DormDriver. */
        driver.displayDorms(dorms);

        guests = null;
        dorms = null;
        System.gc();
    }
}