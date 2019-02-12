Feature: Clients are able to retrieve all or a specific accounts. When the client sends an valid request,
	they get a response indicating that the result is the right one. Clients are also able to create an account
	given they have provided valid and required details, otherwise they get a response indication the error.

	Scenario: Retrieving all Accounts
		When the client send a request to retrieve all accounts
		Then the user gets status code of 200
		And the user get list of all the accounts