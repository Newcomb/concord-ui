package model;

import java.util.ArrayList;
import java.util.List;

public class RSPLSGameLog extends GameLog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761812333666960692L;

	public RSPLSGameLog(List<Integer> users)
	{
		
		super(users);
		System.out.println(users);
		this.moves = new ArrayList<String>() {{
			add("Rock");
			add("Spock");
			add("Scissors");
			add("Lizard");
			add("Paper");
		}};
		initMap(users);
	}

	public RSPLSGameLog()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initMap(List<Integer> users) {
		for (int u: users) {
			this.roundWinTracker.put(u, 1);
			this.playerMoves.put(u, "None");
		}
	}

	@Override
	Boolean checkMove(String move)
	{
		System.out.println(moves.contains(move));
		if (winner == true) {
			winner = false;
			for (int u: roundWinTracker.keySet()) {
				this.roundWinTracker.put(u, 1);
				this.playerMoves.put(u, "None");
			}
		}
		return moves.contains(move);
	}

	@Override
	Boolean checkPlayer(int userID)
	{
		return roundWinTracker.containsKey(userID);
	}

	@Override
	Boolean playMove(String move, int userID)
	{
		playerMoves.put(userID, move);
		return null;
	}
	
	
	// Doesnt need to be implemented for RSPLS
	@Override
	Boolean checkPlayerCalled(int userID)
	{
		return null;
	}

	// Will check if all moves are in and perform logic
	// Parameters are not necessary here but may be for other games
	@Override
	void performSpecialAction(String move, int userID)
	{
		Boolean flag = true;
		for (int key: playerMoves.keySet()) {
			if (playerMoves.get(key).equals("None")) {
				flag = false;
			}
		}
		if (flag) {
			for (int key: playerMoves.keySet()) {
				roundWinTracker.put(key, 0);
				for (int secKey: playerMoves.keySet()) {
					if (playerMoves.get(key).equals("Rock")){
						if (playerMoves.get(secKey).equals("Spock") || playerMoves.get(secKey).equals("Paper")) {
							roundWinTracker.put(key, -1);
						}
					}
					if (playerMoves.get(key).equals("Paper")) {
						if (playerMoves.get(secKey).equals("Scissors") || playerMoves.get(secKey).equals("Lizard")) {
							roundWinTracker.put(key, -1);
						}
					}
					if (playerMoves.get(key).equals("Scissors")) {
						if (playerMoves.get(secKey).equals("Rock") || playerMoves.get(secKey).equals("Spock")) {
							roundWinTracker.put(key, -1);
						}
					}
					if (playerMoves.get(key).equals("Lizard")) {
						if (playerMoves.get(secKey).equals("Scissors") || playerMoves.get(secKey).equals("Rock")) {
							roundWinTracker.put(key, -1);
						}
					}
					if (playerMoves.get(key).equals("Spock")) {
						if (playerMoves.get(secKey).equals("Paper") || playerMoves.get(secKey).equals("Lizard")) {
							roundWinTracker.put(key, -1);
						}
					}
				}
			}
		}
	}

	@Override
	List<Integer> checkForWinner()
	{
		List<Integer> winners = new ArrayList<Integer>();
		Boolean flag = false;
		int track = 0;
		System.out.println("Checking for winnner");
		for (int key: roundWinTracker.keySet()) {
			if (roundWinTracker.get(key) == 0) {
				System.out.println("Winner: " + key);
				winners.add(key);
				this.winner = true;
				System.out.println("Winner found");
				
			}
			if (roundWinTracker.get(key) == -1) {
				track++;
			}
		}
		if (track == roundWinTracker.size()) {
			this.winner = true;
		}
		return winners;
	}

	@Override
	String getPlayerMoves(int userID)
	{
		String m = "Available Moves: "  + moves.get(0);
		for (int i = 1; i < 5; i++) {
			m = m + ", " + moves.get(i);
		}
		return m;
	}

	// Doesnt need to be implemented but would be used in go-fish
	@Override
	Boolean restart()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
