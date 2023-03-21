import java.util.ArrayList;
import java.util.List;


public class MonthlyReport {
    ArrayList<MonthLine> monthReport;

    //Конструктор
    MonthlyReport(List<String> file){
        monthReport = new ArrayList<>();
        if(!file.isEmpty()){
            setItems(file);
        }
    }

    //Записать данные из файла
    void setItems(List<String> file){
        for(int i=1; i<file.size(); i++){
            String[] lineContents = file.get(i).split(",");
            MonthLine line = new MonthLine();
            line.setItem_name(lineContents[0]);
            line.setIs_expense(Boolean.valueOf(lineContents[1]));
            line.setQuantity(Integer.valueOf(lineContents[2]));
            line.setSum_of_one(Integer.valueOf(lineContents[3]));
            monthReport.add(line);
        }
    }

    //Вывести отчет за месяц
    void getReport(){
        System.out.print("Самая большая трата: ");
        getMaxByType(true);
        System.out.print("Самый прибыльный товар: ");
        getMaxByType(false);
    }

    //Получить сумму по доходам/расходам
    int getSum(boolean isExpense){
        int sum=0;
        if(isExpense){
            for(MonthLine line: monthReport){
                if(line.getIs_expense()){
                    sum+=line.getSum_of_one()*line.getQuantity();
                }
            }
        }
        else{
            for(MonthLine line: monthReport){
                if(!line.getIs_expense()){
                    sum+=line.getSum_of_one()*line.getQuantity();
                }
            }
        }
        return sum;
    }

    //Вывести максимальное по расходам/доходам
    void getMaxByType(boolean isExpense){
        int maxSum = 0;
        String maxItem = null;
        if(isExpense){
            for(MonthLine line: monthReport){
                if(line.getIs_expense()){
                    int sum = line.getSum_of_one()*line.getQuantity();
                    if(maxSum<sum) {
                        maxSum = sum;
                        maxItem = line.getItem_name();
                    }
                }
            }
        }
        else{
            for(MonthLine line: monthReport){
                if(!line.getIs_expense()){
                    int sum = line.getSum_of_one()*line.getQuantity();
                    if(maxSum<sum) {
                        maxSum = sum;
                        maxItem = line.getItem_name();
                    }
                }
            }
        }
        System.out.println(maxItem+"= "+maxSum);
    }
}
