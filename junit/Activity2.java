package Activities;

import org.junit.jupiter.api.Test;
import Activities.BankAccount;
import Activities.NotEnoughFundsException;
import static org.junit.jupiter.api.Assertions.*;

class Activity2 {

    @Test
    void notEnoughFunds() {
        BankAccount account = new BankAccount(9);

        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10),
                "Balance must be greater than amount of withdrawal");
    }

    @Test
    void enoughFunds() {
        BankAccount account = new BankAccount(100);

        assertDoesNotThrow(() -> account.withdraw(100));
    }
}

class BankAccount {
    private Integer balance;

    public BankAccount(Integer initialBalance) {
        balance = initialBalance;
    }

    public Integer withdraw(Integer amount) {
        if (balance < amount) {
            throw new NotEnoughFundsException(amount, balance);
        }
        balance -= amount;
        return balance;
    }
}

class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException(Integer amount, Integer balance) {
        super("Attempted to withdraw " + amount + " with a balance of " + balance);
    }

}
