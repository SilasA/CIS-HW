/**
 * Entry point of the application.
 *
 * @author Silas Agnew
 * @version 1.0.0
 */
public class Main
{
	public static void main(String[] args)
	{
		MyPhone phone = new MyPhone("Stephe Stephanson", "5555555555");
		
		phone.chargeBattery(120);
		phone.streamAudio(100);
		phone.sendText("Hello?");
		phone.sendText("Hello?");
		phone.sendText("Hello?");
		phone.sendText("Hello?");
		phone.readText();
		phone.readText();
		phone.readText();
		phone.readText();
		phone.printStatement();
	}
}
