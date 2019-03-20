/**
 * Entry-point of the application
 * Also serves as a tester for {@code BabyNamesDatabase}
 *
 * @author Silas Agnew
 * @version November 9, 2017
 */

import java.util.ArrayList;

public class BabyTest
{
	public static void main(String[] args)
	{
		BabyNamesDatabase db = new BabyNamesDatabase();
		db.readBabyNameData("testCSV.csv");
		//db.printNames();
		
		assert (db.countAllNames() == 7)
				: "Db only supposed to have 7 entries, has: " + db.countAllNames();
		assert (db.mostPopularBoy(1999).getName().equalsIgnoreCase("Silas"))
				: "Most popular boy in 1999 is Silas, not: " +
				db.mostPopularBoy(1999).getName();
		assert (db.countAllGirls() == 2)
				: "Supposed to be 2 girls, there are " + db.countAllGirls();
		assert (db.countAllBoys() == 5)
				: "Supposed to be 5 boys, there are " + db.countAllBoys();
		ArrayList<BabyName> list;
		assert ((list = db.searchForYear(1999)).size() == 2)
				: "Result count for 1999 supposed to be 2, not " + list.size();
		assert (db.countAllBoys() == 1434276447)
				: "Boy count totals to 1434276447, not " + db.countAllBoys();
	}
}
