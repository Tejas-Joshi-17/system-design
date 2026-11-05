// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


package com.sarvatra.design.pattern.liskov.substitution.principal;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LSPViolated {

    private static final Logger log = LoggerFactory.getLogger(LSPViolated.class);

    // Account Interface
    interface Account {
        void deposit(double amount);
        void withdraw(double amount);
    }

    static class SavingAccount implements Account {
        private double balance;

        public SavingAccount() {
            balance = 0;
        }

        @Override
        public void deposit(double amount) {
            balance += amount;
            log.info("Deposited : {}  in Savings Account. New Balance: {}", amount, balance);
        }

        @Override
        public void withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                log.info("Withdrawn : {}  from Savings Account. New Balance: {}", amount, balance);
            } else {
                log.info("Insufficient funds in Savings Account!");
            }
        }
    }

    static class CurrentAccount implements Account {
        private double balance;

        public CurrentAccount() {
            balance = 0;
        }

        @Override
        public void deposit(double amount) {
            balance += amount;
            log.info("Deposited : {}  in Current Account. New Balance: {}", amount, balance);
        }

        @Override
        public void withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                log.info("Withdrawn : {}  from Current Account. New Balance: {}", amount, balance);
            } else {
                log.info("Insufficient funds in Current Account!");
            }
        }
    }

    static class FixedTermAccount implements Account {
        private double balance;

        public FixedTermAccount() {
            balance = 0;
        }

        @Override
        public void deposit(double amount) {
            balance += amount;
            log.info("Deposited : {}  in Fixed Term Account. New Balance: {}", amount, balance);
        }

        @Override
        public void withdraw(double amount) {
            throw new UnsupportedOperationException("Withdrawal not allowed in Fixed Term Account!");
        }
    }

    @RequiredArgsConstructor
    static class BankClient {
        private final List<Account> accounts;

        public void processTransactions() {
            for (Account acc : accounts) {
                acc.deposit(1000);  // All accounts allow deposits

                // Assuming all accounts support withdrawal (LSP Violation)
                try {
                    acc.withdraw(500);
                } catch (UnsupportedOperationException e) {
                    log.info("Exception : {}", e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingAccount());
        accounts.add(new CurrentAccount());
        accounts.add(new FixedTermAccount());

        BankClient client = new BankClient(accounts);
        client.processTransactions(); // Throws exception when withdrawing from FixedTermAccount
    }

}


// 23:55:57.162 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Deposited : 1000.0  in Savings Account. New Balance: 1000.0
// 23:55:57.164 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Withdrawn : 500.0  from Savings Account. New Balance: 500.0
// 23:55:57.164 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Deposited : 1000.0  in Current Account. New Balance: 1000.0
// 23:55:57.164 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Withdrawn : 500.0  from Current Account. New Balance: 500.0
// 23:55:57.164 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Deposited : 1000.0  in Fixed Term Account. New Balance: 1000.0
// 23:55:57.164 [main] INFO c.s.d.p.l.s.p.LSPViolated -- Exception : Withdrawal not allowed in Fixed Term Account!


// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------