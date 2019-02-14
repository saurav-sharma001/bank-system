Feature: Clients are able to retrieve all or a specific accounts. When the client sends an valid request,
	they get a response indicating that the result is the right one. Clients are also able to create an account
	given they have provided valid and required details, otherwise they get a response indication the error.

	Scenario: Retrieving all Accounts
		When the client send a request to retrieve all accounts
		Then the client get total of 2 accounts
		
	Scenario: Account Creation
		Given account detail: accountNumber: 20, firstName: Saurav, lastName: Sharma, email: sauravsharma001@gmail.com
		When the client tries to create bank account
		Then the client should receive "Bank Account Successfully Created" message 