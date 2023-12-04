package database;

import java.util.Date;

public class ExpenseEntity {
    public int Id;
    public String expenseName;
    public String expenseDate;
    public String expenseType;
    public String amount;

    public ExpenseEntity() {

    }

    public ExpenseEntity(int id, String expenseName, String expenseDate, String amount ,String expenseType) {
        Id = id;
        this.expenseName = expenseName;
        this.expenseDate = expenseDate;
        this.amount = amount;
        this.expenseType = expenseType;
    }

    @Override
    public String toString() {
        return expenseName + "\n " +amount+ "\n" +expenseDate;
    }
}
