import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Converter {
    //Конвертер номера месяца в имя
    public String getNameMonth(int month){
        Month printerMonth = Month.of((month));
        Locale loc = Locale.forLanguageTag("ru");
        return printerMonth.getDisplayName(TextStyle.FULL_STANDALONE, loc);
    }
    public String getExpensiveOrIncome(boolean type){
        if(type){
            return "Расход";
        }
        else{
            return "Доход";
        }
    }
}
