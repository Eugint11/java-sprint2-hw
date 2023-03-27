package monthReport;

public class MonthLine {
    private String item_name;
    private Boolean is_expense;
    private Integer quantity;
    private Integer sum_of_one;

    //Конструктор
    MonthLine(){
        item_name = null;
        is_expense=true;
        quantity = 0;
        sum_of_one = 0;
    }

    public String getItem_name() { return item_name; }

    public void setItem_name(String item_name) { this.item_name = item_name; }

    public Boolean getIs_expense() {
        return is_expense;
    }

    public void setIs_expense(Boolean is_expense) {
        this.is_expense = is_expense;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSum_of_one() {
        return sum_of_one;
    }

    public void setSum_of_one(Integer sum_of_one) {
        this.sum_of_one = sum_of_one;
    }
}
