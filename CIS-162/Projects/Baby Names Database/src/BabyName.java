/**
 * Class that contains data on a baby name.
 * Contains the name and gender of the baby and the year and the number of
 * babies born in that year
 *
 * @author Silas Agnew
 * @version November 9, 2017
 */

public class BabyName implements Comparable
{
    String name     = null;
    boolean gender  = false;
    int count       = -1;
    int year        = -1;

    //-Constructor(s)----------------------------------------------//

    /**
     * Constructs a {@code BabyName} object that contains info about a baby name
     * @param name The name of the baby
     * @param gender Probable gender of the name
     * @param count Number of babies born in {@code year}
     * @param year Year the babies were born
     */
    public BabyName(String name, boolean gender, int count, int year)
    {
        setName(name);
        setGender(gender);
        setCount(count);
        setYear(year);
    }

    /**
     * Takes a line of CSV data and parses it into usable data in
     * {@code BabyName}.
     * @param csvLine Line of CSVs
     * @return A constructed {@code BabyName} from parsed csv data
     * @throws IllegalArgumentException 1) if there is not 4 CSVs in the string
     *  2) if there is an invalid numeric value (i.e. negative)
     */
    public static BabyName BabyNameBuilder(String csvLine, int lineNum)
            throws IllegalArgumentException
    {
        boolean gender = false;
        int count = 0;
        int year = 0;

        String[] csv = csvLine.split("[,]+");
        if (csv.length != 4)
            throw new IllegalArgumentException(
                "CSV line " + lineNum + " is ill-formatted: must contain 4 values");

        // Gender
        if (csv[1].equalsIgnoreCase("M")) gender = false;
        else if (csv[1].equalsIgnoreCase("F")) gender = true;
        else throw new IllegalArgumentException(
                "Gender (line: " + lineNum + ", col: 2) contains neither M or F");

        // Count
        count = Integer.parseInt(csv[2]);
        if (count <= 0) throw new IllegalArgumentException(
                "Birth count (line: " + lineNum +
                        ", col: 2) cannot be a negative number.");

        // Year
        year = Integer.parseInt(csv[3]);
        if (year <= 0) throw new IllegalArgumentException(
                "Birth year (line: " + lineNum +
                        ", col: 2) cannot be a negative number.");

        return new BabyName(csv[0], gender, count, year);
    }

    //-Accessors---------------------------------------------------//

    /**
     * @return If the baby name is a female name.
     */
    public boolean isFemale() { return gender; }

    /**
     * @return The baby's name.
     */
    public String getName() { return name; }

    /**
     * @return The birth count of the name.
     */
    public int getCount() { return count; }

    /**
     * @return The year of the name.
     */
    public int getYear() { return year; }

    //-Mutators----------------------------------------------------//

    /**
     * Sets the baby name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the baby gender.
     */
    public void setGender(boolean gender) { this.gender = gender; }

    /**
     * Sets the baby birth count.
     */
    public void setCount(int count) { this.count = count; }

    /**
     * Sets the baby birth year.
     */
    public void setYear(int year) { this.year = year; }

    /**
     * @return A formatted sentence interpreting the data in the class
     */
    @Override
    public String toString()
    {
       return count + (isFemale() ? " girls" : " boys") +
               " named " + name + " in " + year;
    }

    /**
     * Compares 2 {@code BabyName} objects by popularity (birth count).
     * @param other {@code BabyName} to compare this to.
     * @return {@code n > 0} if {@code other} is greater than {@code this}
     */
    @Override
    public int compareTo(Object other)
    {
        return ((BabyName)other).count - count;
    }
}
