package banking.primitive.core;

/*
 * Authors: Brandon and Taylor
 * Date 2/20/2017
 * 
 * Description: singleton sever factory for account
 */
public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
