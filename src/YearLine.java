import java.util.List;

public class YearLine {

    private String month;
    private Integer amount;
    private Boolean is_expense;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }



    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getIs_expense() {
        return is_expense;
    }

    public void setIs_expense(Boolean is_expense) {
        this.is_expense = is_expense;
    }

    YearLine(){
        month = "01";
        amount=0;
        is_expense=true;
    }
}
