import java.util.*;
public class DormDriver
{
    public void display(Room room)
    {
        /* display all the guests in the room */
        int i;
        for (i = 0; i < room.getNumOccupants(); i++){
            System.out.println(room.getGuests()[i].getName() + ", " + room.getGuests()[i].getNationality());
        }
    }

    public void displayDorms(Dormitory[] dorms)
    {
        int i, j, k;
        for (i = 0; i < dorms.length; i++)
        {
			/* display the name of the dorm, the total
			   number of rooms, and the number of rooms
			   that are not full yet */
            System.out.println(dorms[i].getName());
			
			/* 
			   Display all the names and nationalities
			   of the guests in each room. Part of the 
			   solution is to call the method display() 
			   in DormDriver. Provide your code */
            for (j = 0; j < dorms[i].getRooms().length; j++){
                System.out.println("Room " + dorms[i].getRooms()[j].getRoomNum() + " guests: ");
                display(dorms[i].getRooms()[j]);
                System.out.println();
            }

        }
    }


    public static void main(String[] args)
    {
        Dormitory[] dorms = new Dormitory[2];

        dorms[0] = new Dormitory("LS Dorm", 3);
        dorms[1] = new Dormitory("STC Dorm", 5, 4);

        ArrayList<Person> guests = new ArrayList<Person>();
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
		   separate rooms. Provide your code.
		*/
        int i;
        int j = 0;
        for (i = 0; i < guests.size(); i++){
            if (guests.get(i) != null && guests.get(i).getNationality().equalsIgnoreCase("Filipino")){
                if (dorms[0].acceptGuests(1)){
                    dorms[0].getRooms()[0].addGuest(guests.get(i));
                }
                else{
                    dorms[0].getRooms()[1].addGuest(guests.get(i));
                }
            }
            else {
                for (j = 0; j < dorms[1].getRooms().length; j++){
                    if (guests.get(i) != null && dorms[1].getRooms()[j].isEmpty()){
                        dorms[1].getRooms()[j].addGuest(guests.get(i));
                        break;
                    }
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
        System.out.println("\n\nTransfering Ray");
	    dorms[0].getRooms()[0].removeGuest(3);
        for (i = 0; i < dorms[1].getRooms().length; i++){
            if (dorms[1].getRooms()[i].isEmpty()){
                dorms[1].getRooms()[i].addGuest(guests.get(3));
                break;
            }
        }
		
		/* Provide code to transfer Michael to the same room 
		   as Miguel */
        System.out.println("\n\nTransfering Michael");
		dorms[1].getRooms()[1].removeGuest(0);
        dorms[0].getRooms()[0].addGuest(guests.get(5));
	
		

		/* Provide your code to call displayDorms() in
		   class DormDriver. */
        driver.displayDorms(dorms);


        guests = null;
        dorms = null;
        System.gc();
    }
}