// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

package com.sarvatra.design.pattern.liskov.substitution.principal;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// 1. DepositOnlyAccount interface : Only Allows Deposits
interface DepositOnlyAccount {
    Logger log = LoggerFactory.getLogger(DepositOnlyAccount.class);

    void deposit(double amount);
}

// 2. WithdrawableAccount interface : Allows Deposits and Withdrawals
interface WithdrawableAccount extends DepositOnlyAccount {
    void withdraw(double amount);
}


class SavingAccount implements WithdrawableAccount {
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
            log.info("Withdrawn : {} from Savings Account. New Balance: {}", amount, balance);
        } else {
            log.info("Insufficient funds in Savings Account!");
        }
    }
}


class CurrentAccount implements WithdrawableAccount {
    private double balance;

    public CurrentAccount() {
        balance = 0;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        log.info("Deposited : {} in Current Account. New Balance: {}", amount, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            log.info("Withdrawn : {} from Current Account. New Balance: {}", amount, balance);
        } else {
            log.info("Insufficient funds in Current Account!");
        }
    }
}


class FixedTermAccount implements DepositOnlyAccount {
    private double balance;

    public FixedTermAccount() {
        balance = 0;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        log.info("Deposited : {} in Fixed Term Account. New Balance: {}", amount, balance);
    }
}

@RequiredArgsConstructor
class BankClient {
    private final List<WithdrawableAccount> withdrawableAccounts;
    private final List<DepositOnlyAccount> depositOnlyAccounts;

    public void processTransactions() {
        for (WithdrawableAccount acc : withdrawableAccounts) {
            acc.deposit(1000);
            acc.withdraw(500);
        }
        for (DepositOnlyAccount acc : depositOnlyAccounts) {
            acc.deposit(5000);
        }
    }
}

public class LSPFollowed {

    public static void main(String[] args) {
        List<WithdrawableAccount> withdrawableAccounts = new ArrayList<>();
        withdrawableAccounts.add(new SavingAccount());
        withdrawableAccounts.add(new CurrentAccount());

        List<DepositOnlyAccount> depositOnlyAccounts = new ArrayList<>();
        depositOnlyAccounts.add(new FixedTermAccount());

        BankClient client = new BankClient(withdrawableAccounts, depositOnlyAccounts);
        client.processTransactions();
    }

}

// 00:03:33.709 [main] INFO c.s.d.p.l.s.p.DepositOnlyAccount -- Deposited : 1000.0  in Savings Account. New Balance: 1000.0
// 00:03:33.711 [main] INFO c.s.d.p.l.s.p.DepositOnlyAccount -- Withdrawn : 500.0 from Savings Account. New Balance: 500.0
// 00:03:33.711 [main] INFO c.s.d.p.l.s.p.DepositOnlyAccount -- Deposited : 1000.0 in Current Account. New Balance: 1000.0
// 00:03:33.711 [main] INFO c.s.d.p.l.s.p.DepositOnlyAccount -- Withdrawn : 500.0 from Current Account. New Balance: 500.0
// 00:03:33.711 [main] INFO c.s.d.p.l.s.p.DepositOnlyAccount -- Deposited : 5000.0 in Fixed Term Account. New Balance: 5000.0


// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------