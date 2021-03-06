package banking.primitive.core;
/*
 * Authors: Taylor and Brandon 
 * Date: 2/20/2017
 * 
 * Description: Checking account
 */



/*
 * Description: Allows the user to deposit or withdraw from a checking account type.
 */
public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int numWithdraws = 0;

	/**
	  Method: getType()
	  Returns: type "Checking"

	  Description: return "Checking" as account type to appropriate method
	*/
	public String getType() { return "Checking"; }
	
	private Checking(String name) {
		super(name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

	public Checking(String name, float balance) {
		super(name, balance);
	}
	
	public String getType() { return "Checking"; }

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {

		if (getState() != STATE.CLOSED && amount >= 0.0f) { // <-- Fixed 2/20/2017

			balance = balance + amount;
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == STATE.OPEN || (getState() == STATE.OVERDRAWN && balance > -100.0f)) {
				balance = balance - amount;
				numWithdraws++;
				if (numWithdraws > 10){
					balance = balance - 2.0f;
				}
				if (balance < 0.0f) {
					setState(STATE.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	
	/**
	  Method: toString()
	  Inputs: getName(), getBalance()
	  Returns: String

	  Description: returns name of account and corresponding balance
	*/
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
