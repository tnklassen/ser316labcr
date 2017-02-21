package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.STATE;


/*
 * Authors: Taylor and Brandon 
 * Date: 2/20/2017
 * 
 * Description: ServerSolution
 */




/*
 * Description: allows for different kinds of accounts to be called
 */

class ServerSolution implements AccountServer {

	static String fileName = "accounts.ser";

	Map<String,Account> accountMap = null;

	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null){
						accountMap.put(acc.getName(), acc);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	/**
	  Method: getAccount()
	  Inputs: String name
	  Returns: name of account

	  Description: name of account used to identify name to be returned in map
	*/
	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	/**
	  Method: getAllAccounts()
	  Returns: list of accounts

	  Description: all accounts within system will be listed
	*/
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	/**
	  Method: getActiveAccounts()
	  Returns: list of user information

	  Description: all accounts with state not currently set to "Closed" will be listed
	*/
	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != STATE.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	
	/**
	  Method: newAccount
	  Inputs: String type, String name, float balance
	  Returns: new user account

	  Description: returns a new user account with a type, name, and balance
	*/
	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		
		if (balance < 0.0f) throw new IllegalArgumentException("New account may not be started with a negative balance");
		
		return _newAccountFactory(type, name, balance);
	}
	
	/**
	  Method: closeAccount()
	  Inputs: String name
	  Returns: boolean true or false

	  Description: sets state of user account to "Closed" if account is null
	*/
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(STATE.CLOSED);
		return true;
	}

	/**
	  Method: saveAccounts()

	  Description: will save all accounts to accountMap
	*/
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (int i=0; i < accountMap.size(); i++) {
				out.writeObject(accountMap.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	/**
	  Method:_newAccountFactory()
	  Inputs: String type, String name, float balance
	  Returns: boolean true or false

	  Description: creates new account for user based on user-chosen preferences.
	*/
	private boolean _newAccountFactory(String type, String name, float balance)
			throws IllegalArgumentException {
			
			if (accountMap.get(name) != null) return false;
			
			Account acc;
			if ("Checking".equals(type)) {
				acc = new Checking(name, balance);

			} else if ("Savings".equals(type)) {
				acc = new Savings(name, balance);

			} else {
				throw new IllegalArgumentException("Bad account type:" + type);
			}
			try {
				accountMap.put(acc.getName(), acc);
			} catch (Exception exc) {
				return false;
			}
			return true;
		}

}
