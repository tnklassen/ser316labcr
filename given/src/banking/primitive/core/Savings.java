package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int _numWithdraws = 0;

	/**
	  Method: getType()
	  Returns: type "Savings"

	  Description: return "Savings" as account type to appropriate method
	*/
	public String getType() { return "Savings"; } // <-- Fixed 2/20/2017
	
	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
			return true; // <- Fixed 2/20/2017
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == STATE.OPEN && amount > 0.0f) {
			balance = balance - amount;
			_numWithdraws++;
			if (_numWithdraws > 3){
				balance = balance - 1.0f;
			}
			// KG BVA: should be < 0


			if (balance < 0.0f) { // <- Fixed 2/20/2017
				setState(STATE.OVERDRAWN);

			}
			return true;
		}
		return false;
	}

	/**
	  Method: toString()
	  Inputs: getName(), getBalance()
	  Returns: string

	  Description: return name of account and corresponding balance
	*/
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
