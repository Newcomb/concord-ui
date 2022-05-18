package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GameLog implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 712810430638094913L;
	public List<String> moves = new ArrayList<String>();
	public HashMap<Integer, Integer> roundWinTracker = new HashMap<Integer, Integer>();
	// Will have different implementations for different games 
	public HashMap<Integer, String> playerMoves = new HashMap<Integer, String>();
	public Boolean winner = false;

	public GameLog(List<Integer> users) {
		super();
		initMap(users);
		winner = false;
	}
	
	public GameLog()
	{
		super();
	}
	
	public void initMap(List<Integer> users) {
		for (int u: users) {
			this.roundWinTracker.put(u, 0);
		}
	}
	
	public final Boolean performMove(String move, int userID) {
		if (checkMove(move) && checkPlayer(userID)) {
			playMove(move, userID);
			performSpecialAction(move, userID);
			checkForWinner();
			return true;
		}
		return false;
	}
	
	// This function could be different depending on if there is a top card or other factors
	abstract Boolean checkMove(String move);
	
	abstract Boolean checkPlayer(int userID);
	
	abstract Boolean playMove(String move, int userID);
	
	// Only used in Go-Fish or other types
	abstract Boolean checkPlayerCalled(int userID);
	
	
	// String only necessary in games like uno
	abstract void performSpecialAction(String move, int userID);
	
	
	abstract List<Integer> checkForWinner();
	
	abstract String getPlayerMoves(int userID);
	
	// restarts the game
	abstract Boolean restart();

	/**
	 * @return the moves
	 */
	public List<String> getMoves()
	{
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(List<String> moves)
	{
		this.moves = moves;
	}

	/**
	 * @return the roundWinTracker
	 */
	public HashMap<Integer, Integer> getRoundWinTracker()
	{
		return roundWinTracker;
	}

	/**
	 * @param roundWinTracker the roundWinTracker to set
	 */
	public void setRoundWinTracker(HashMap<Integer, Integer> roundWinTracker)
	{
		this.roundWinTracker = roundWinTracker;
	}

	/**
	 * @return the playerMoves
	 */
	public HashMap<Integer, String> getPlayerMoves()
	{
		return playerMoves;
	}

	/**
	 * @param playerMoves the playerMoves to set
	 */
	public void setPlayerMoves(HashMap<Integer, String> playerMoves)
	{
		this.playerMoves = playerMoves;
	}

	/**
	 * @return the winner
	 */
	public Boolean getWinner()
	{
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Boolean winner)
	{
		this.winner = winner;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moves == null) ? 0 : moves.hashCode());
		result = prime * result + ((playerMoves == null) ? 0 : playerMoves.hashCode());
		result = prime * result + ((roundWinTracker == null) ? 0 : roundWinTracker.hashCode());
		result = prime * result + ((winner == null) ? 0 : winner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameLog other = (GameLog) obj;
		if (moves == null)
		{
			if (other.moves != null)
				return false;
		} else if (!moves.equals(other.moves))
			return false;
		if (playerMoves == null)
		{
			if (other.playerMoves != null)
				return false;
		} else if (!playerMoves.equals(other.playerMoves))
			return false;
		if (roundWinTracker == null)
		{
			if (other.roundWinTracker != null)
				return false;
		} else if (!roundWinTracker.equals(other.roundWinTracker))
			return false;
		if (winner == null)
		{
			if (other.winner != null)
				return false;
		} else if (!winner.equals(other.winner))
			return false;
		return true;
	}

	
}
