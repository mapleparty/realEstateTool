/**
 * The class offer stores the information of an offer
 */
package realEstate;

public class Offer {
	
	//Fields
	private String maker; //The offer maker
	private int amount; //How much
	private boolean accepted; //Is accepted?
	private boolean refused; //Is refused?
	
	
	/**
	 * The constuctor
	 * Creates a new offer object
	 * @param maker
	 * @param amount
	 * @param accepted
	 * @param refused
	 */
	public Offer(String maker, int amount, boolean accepted, boolean refused) {
		
		//Initialize fields
		this.maker=maker;
		this.amount=amount;
		this.accepted=accepted;
		this.refused=refused;
	}

	/**
	 * Getter for the maker
	 * @return
	 */
	public String getMaker() {
		return maker;
	}

	/**
	 * Setter for the maker
	 * @param maker
	 */
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	/**
	 * Getter for the offer amount
	 * @return
	 */

	public int getAmount() {
		return amount;
	}
	
	/**
	 * Setter for the offer amount
	 * @param amount
	 */

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * Getter whether the offer is accepted
	 * @return
	 */

	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * Setter whether the offer is accepted
	 * @param accepted
	 */

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * Getter whether the offer is refused
	 * @return
	 */

	public boolean isRefused() {
		return refused;
	}

	/**
	 * Setter whether the offer is refused
	 * @param refused
	 */
	public void setRefused(boolean refused) {
		this.refused = refused;
	}

	
}
