insert into users(userId, firstName, lastName, email, password) values (USER_SEQ.nextval, 'jan', 'stanton', 'jansbademail@gmail.com','imagoth')
insert into users(userId, firstName, lastName, email, password) values (USER_SEQ.nextval, 'iain', 'prentice', 'woodn@gmail.com','pog')

insert into currentaccount(accountId, accountName, balance, interestRate, overdraftAmount, userId) values (ACCOUNT_SEQ.nextval, 'Flex', '1200', '0.5', '100', 1)
insert into currentaccount(accountId, accountName, balance, interestRate, overdraftAmount, userId) values (ACCOUNT_SEQ.nextval, 'Debit', '600', '2.5', '300', 2)

insert into savingsaccount(accountId, accountName, balance, interestRate, accessType, userId) values (ACCOUNT_SEQ.nextval, 'CashBuilder', '800', '3.5', 'Unlimited', 1)

insert into mortgageaccount(accountId, accountName, balance, interestRate, contractLength, nextPaymentDate, nextPaymentAmount, userId) values (ACCOUNT_SEQ.nextval, 'Mortgage', '100800', '2', '14', '14 September', '650.49', 1)
insert into mortgageaccount(accountId, accountName, balance, interestRate, contractLength, nextPaymentDate, nextPaymentAmount, userId) values (ACCOUNT_SEQ.nextval, 'Mortgage', '80000', '3.4', '10', '25 September', '400.37', 2)

insert into currenttransaction(currentTransactionId, date, description, type, status, amount, accountId) values (CURRENTTRANSACTION_SEQ.nextval, TO_DATE('2020/09/15', 'yyyy/mm/dd'), 'McDonalds', 'Outgoing', 'Approved', '4.99', 1)
insert into currenttransaction(currentTransactionId, date, description, type, status, amount, accountId) values (CURRENTTRANSACTION_SEQ.nextval, TO_DATE('2020/09/15', 'yyyy/mm/dd'), 'KFC', 'Outgoing', 'Approved', '9.99', 1)
insert into currenttransaction(currentTransactionId, date, description, type, status, amount, accountId) values (CURRENTTRANSACTION_SEQ.nextval, TO_DATE('2020/09/15', 'yyyy/mm/dd'), 'Morisons', 'Outgoing', 'Approved', '21.97', 1)