/**
 * A game of pig.
 * This includes all the functionality needed for a 2-die game of pig.
 * This is to be manipulate by {@code PigGUI} or run autonomously with the
 * {@code autoGame()} method.
 *
 * Pig Rules:
 *  -A player rolls 2 die until they decide to hold or they roll 1 or 2 ones
 *  -If they roll a single 1, they lose all of the current rounds score
 *  	and the turn ends
 * 	-If they roll double 1s, they lose their entire score and the turn ends
 * 	-If they hold they get the current round's points added to their total
 * 		score
 * 	-The 1st player to 100 wins
 *
 * @author Silas Agnew
 * @version October 29, 2017
 */

public class PigGame
{
	private static final int WINNING_SCORE = 100;
	private GVdie die1 			  = null;
	private GVdie die2 			  = null;
	private int playerScore 	  = 0;
	private int cpuScore 		  = 0;
	private int currentRoundScore = 0;
	private boolean playerTurn 	  = true;
	
	/**
	 * Constructs a {@code PigGame} object
	 */
	public PigGame()
	{
		die1 = new GVdie();
		die2 = new GVdie();

		System.out.println("Welcome to Silas's 2-die Game of Pig.");
	}
	
	//-Accessors---------------------------------------------------//

	/**
	 * @return Player's score
	 */
	public int getPlayerScore() { return playerScore; }

	/**
	 * @return CPU's score
	 */
	public int getComputerScore() { return cpuScore; }

	/**
	 * @return Score of the current round
	 */
	public int getCurrentRoundScore() { return currentRoundScore; }

	/**
	 * @return If it is the player's turn
	 */
	public boolean isPlayerTurn() { return playerTurn; }

	/**
	 * @return If the player has won the game
	 */
	public boolean playerWon()
	{
		return playerScore + (playerTurn ? currentRoundScore : 0) >= WINNING_SCORE;
	}

	/**
	 * @return If the CPU has won the game
	 */
	public boolean computerWon()
	{
		return cpuScore + (!playerTurn ? currentRoundScore : 0) >= WINNING_SCORE;
	}
	
	/**
	 * Gets the die associated with the number passed
	 * To avoid errors, {@code num <= 1} is die 1 and {@code num >= 2} is die 2
	 * @param num Number associated with the die
	 * @return The die
	 */
	public GVdie getDie(int num) { return num >= 2 ? die2 : die1; }
	
	/**
	 * Calls {@link PigGame#rollDice()} method for the turn then accumulates the
	 * score in the case of single or double 1s or victory
	 */
	public void playerRolls()
	{
		playerTurn = true;
		rollDice();
		System.out.println(die1.getValue() + " " + die2.getValue() +
				" Round Score: " + currentRoundScore);
		
		if (!playerTurn || playerWon())
		{
			playerScore += currentRoundScore;
			currentRoundScore = 0;
			playerTurn = false;
			System.out.println("---- Your Score: " + playerScore + "\n");
		}
		
		if (playerWon())
		{
			playerScore += currentRoundScore;
			System.out.println("You Won!");
		}
	}
	
	/**
	 * Combines current and total scores and ends the players turn
	 */
	public void playerHolds()
	{
		playerScore += currentRoundScore;
		currentRoundScore = 0;
		playerTurn = false;
		System.out.println("---- Your Score: " + playerScore + "\n");
	}
	
	/**
	 * Simulates a player's turn
	 * It will hold at 19
	 * @see PigGame#playerRolls() playerRolls
	 * @see PigGame#playerHolds() playerHolds
	 */
	public void computerTurn()
	{
		playerTurn = false;
		do {
			rollDice();
			System.out.println(die1.getValue() + " " + die2.getValue() +
					" Round Score: " + currentRoundScore);
		} while (!playerTurn && currentRoundScore <= 19 && !computerWon());

		playerTurn = true;
		cpuScore += currentRoundScore;
		currentRoundScore = 0;
		System.out.println("---- CPU Score: " + cpuScore + "\n");
		
		if (computerWon())
		{
			cpuScore += currentRoundScore;
			System.out.println("CPU Won!");
		}
	}
	
	/**
	 * Restart the game by resetting all scores and die
	 */
	public void restart()
	{
		die1.setBlank();
		die2.setBlank();
		playerScore = 0;
		cpuScore = 0;
		currentRoundScore = 0;
		playerTurn = true;
	}

	//-Helper/Test Methods---------------------------------//
	
	/**
	 * Rolls the die and handles 1s and computes score
	 */
	private void rollDice()
	{
		die1.roll();
		die2.roll();

		if (die1.getValue() == 1 && die2.getValue() == 1)
		{
			currentRoundScore = 0;
			playerScore = 0;
			playerTurn = !playerTurn;
		}
		else if (die1.getValue() == 1 || die2.getValue() == 1)
		{
			currentRoundScore = 0;
			playerTurn = !playerTurn;
		}
		else
			currentRoundScore += die1.getValue() + die2.getValue();
	}
	
	/**
	 * Simulates a players turn
	 */
	private void playerTurn()
	{
		while (playerTurn)
		{
			if (currentRoundScore < 19)
				playerRolls();
			else playerHolds();
		}
	}
	
	/**
	 * Resets and runs a full game
	 */
	public void autoGame()
	{
		int turns = 0;
		restart();
		
		while (true)
		{
			turns++;
			System.out.println("===== Turn " + turns + " =====");
			playerTurn();
			if (playerWon()) break;
			
			computerTurn();
			if (computerWon()) break;
		}
		System.out.println("Total Turns: " + turns);
	}
}