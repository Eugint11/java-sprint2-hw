import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class YearlyReport {
    private ArrayList<YearLine> yearReport;

    //Получить годовой отчет
    public ArrayList<YearLine> getYearReport() {
        return yearReport;
    }

    //Добавить годовой отчет
    public void setYearReport(ArrayList<YearLine> yearReport) {
        this.yearReport = yearReport;
    }

    //Конструктор годового отчета
    YearlyReport(List<String> file){
        yearReport = new ArrayList<>();
        if(!file.isEmpty()){
            setItems(file);
        }
    }

    //Записать данные из файла
    void setItems(List<String> file){
        for(int i=1; i<file.size(); i++){
            String[] lineContents = file.get(i).split(",");
            YearLine line = new YearLine();
            line.setMonth(Integer.valueOf(lineContents[0]));
            line.setAmount(Integer.valueOf(lineContents[1]));
            line.setIs_expense(Boolean.valueOf(lineContents[2]));
            getYearReport().add(line);
        }
    }

    //Вычислить и вывести годовой отчет
    void getReport(){
        HashMap<Integer, Integer> profit = new HashMap<>();
        int income = 0;
        int expense = 0;
        HashSet<Integer> months = new HashSet<>();
        if(getYearReport().size() == 0){
            System.out.println("Годовой отчет пустой");
            return;
        }
        else {
            for (YearLine line : getYearReport()) {
                months.add(line.getMonth());
            }
            for (int monthNumber : months) {
                Month month = Month.of(monthNumber);
                Locale loc = Locale.forLanguageTag("ru");
                String printMonth = month.getDisplayName(TextStyle.FULL_STANDALONE, loc);
                System.out.println(printMonth.substring(0, 1).toUpperCase() + printMonth.substring(1));

                for (YearLine line : getYearReport()) {
                    if (line.getMonth() == monthNumber && line.getIs_expense()) {
                        expense = line.getAmount();
                    } else if (line.getMonth() == monthNumber) {
                        income = line.getAmount();
                    }
                }
                int ProfitOfMonth = getProfitOfMonth(income, expense);
                profit.put(monthNumber, ProfitOfMonth);
                System.out.println("Прибыль = " + ProfitOfMonth);
            }

            int sum = 0;
            for (int profitLine : profit.values()) {
                sum += profitLine;
            }
            double avg = sum / profit.size();
            System.out.println("Средняя прибыль = " + String.format("%.2f", avg));
            System.out.println("Средний доход = " + String.format("%.2f",getAvByType(false)));
            System.out.println("Средний расход = " + String.format("%.2f",getAvByType(true)));
        }
    }

    //Вычислить прибыль за месяц
    int getProfitOfMonth(int income, int expense){
        int profit = income - expense;
        return profit;
    }

    double getAvByType(boolean isExpensive){
        double sum=0.0;
        int count=0;
        for(YearLine line: getYearReport()){
            if(line.getIs_expense() == isExpensive){
                sum+=line.getAmount();
                count++;
            }
        }
        if(count==0){
            return 0;
        }
        else{
            return sum/count;
        }
    }

    public boolean searchMonth(HashMap<Integer, Integer> monthlyProfit, boolean isExpensive){
        Converter converter = new Converter();
        HashSet<Integer> yearlyLines = new HashSet<>();
        for(YearLine yearLine: getYearReport()){
            if(yearLine.getIs_expense()){
                yearlyLines.add(yearLine.getMonth());
            }
        }
        for(int month: monthlyProfit.keySet()){
            if(yearlyLines.contains(month)) {
                System.out.println("Отсутствует запись о "
                        + converter.getExpensiveOrIncome(isExpensive).toLowerCase()+ "е"
                        + " за "
                        + converter.getNameMonth(month)
                        +" в годовом отчете");
                return false;
            }
        }
        return true;
    }


}
