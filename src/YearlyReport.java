import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {
    private ArrayList<YearLine> yearReport;
    public ArrayList<YearLine> getYearReport() {
        return yearReport;
    }

    public void setYearReport(ArrayList<YearLine> yearReport) {
        this.yearReport = yearReport;
    }


    YearlyReport(List<String> file){
        yearReport = new ArrayList<>();
        if(!file.isEmpty()){
            setItems(file);
        }
    }
    void setItems(List<String> file){
        for(int i=1; i<file.size(); i++){
            String[] lineContents = file.get(i).split(",");
            YearLine line = new YearLine();
            line.setMonth(Integer.valueOf(lineContents[0]));
            line.setAmount(Integer.valueOf(lineContents[1]));
            line.setIs_expense(Boolean.valueOf(lineContents[2]));
            yearReport.add(line);
        }
    }
    void getReport(){
        for(YearLine line: yearReport){
            System.out.println(line.getMonth()+" "+line.getAmount()+" "+line.getIs_expense());
        }
    }
}
