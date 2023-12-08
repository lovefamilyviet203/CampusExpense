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

    public int getId() {
        return Id;
    }
    public String getExpenseName(){
        return expenseName;
    }
    public String getAmount(){
        return amount;
    }
    public String getExpenseDate(){
        return expenseDate;
    }
    public String getExpenseType(){
        return expenseType;
    }

    public void setId(int id) {
        Id = id;
    }
    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    @Override
    public String toString() {
        return expenseName + "\n " +amount+ "\n" +expenseDate;
    }
}
