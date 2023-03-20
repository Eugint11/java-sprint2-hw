import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        for(MonthLine line: monthReport){
            System.out.println(line.getItem_name()+" "+line.getIs_expense()+" "+line.getQuantity()+" "+line.getSum_of_one());
        }
    }

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
}
