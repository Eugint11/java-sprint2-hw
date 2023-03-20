import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
        ArrayList<YearlyReport> yearlyReports = new ArrayList<>();

        while(true){
            printMenu();
            String scan = scanner.next();
            switch(scan){
                case("1"):{
                    System.out.println("Введите год отчетности: ");
                    int year = scanner.nextInt();
                    for(String files: findFiles("m."+year)){
                        //System.out.println(files);
                        List<String> file = readFileContents(files);
                        monthlyReports.add(new MonthlyReport(file));
                    }
                    break;
                }
                case("2"):{
                    System.out.println("Введите год отчетности: ");
                    int year = scanner.nextInt();
                    for(String files: findFiles("y."+year)){
                        //System.out.println(files);
                        List<String> file = readFileContents(files);
                        yearlyReports.add(new YearlyReport(file));
                    }
                    break;
                }
                case("3"):{
                    reconciliation(monthlyReports,yearlyReports.get(0));
                    break;
                }
                case("4"):{
                    for(int i=0; i<monthlyReports.size(); i++) {
                        System.out.printf("%nMecяц "+(i+1)+":%n");
                        monthlyReports.get(i).getReport();
                    }
                    break;
                }
                case("5"):{
                    System.out.printf("%nОтчет за год%n");
                    for(int i=0; i<yearlyReports.size(); i++) {
                        yearlyReports.get(i).getReport();
                    }
                    break;
                }
                case("0"):{
                    System.out.println("До свидания!");
                    return;
                }
                default:{
                    System.out.println("Такой команды не существует");
                    break;
                }
            }

        }
    }

    //Меню
    static void printMenu(){
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("0. Выход");
    }

    //Поиск файлов
    static ArrayList<String> findFiles(String patternFileName){
        Path default_path = Paths.get(".");
        ArrayList<String> simpleStringCollection = new ArrayList<>();
        try {
            Stream<Path> results = Files.find(default_path,
                    Integer.MAX_VALUE,
                    (path, basicFileAttributes) -> path.toFile().getName().matches(patternFileName+".*.csv")
            );
            results.forEach(p -> simpleStringCollection.add(p.toString()));
            return simpleStringCollection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //Прочитать файл
    static List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));

        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    //Сверить годовой и месячные отчеты
    static void reconciliation(ArrayList<MonthlyReport> monthlyReports, YearlyReport yearlyReport){
        HashMap<Integer, Integer> monthlyExpenses = new HashMap<>();
        HashMap<Integer, Integer> monthlyIncomes = new HashMap<>();

        for(int i=0; i<monthlyReports.size(); i++) {
            int income = monthlyReports.get(i).getSum(false);
            int expense = monthlyReports.get(i).getSum(true);

            monthlyIncomes.put((i+1), income);
            monthlyExpenses.put((i+1), expense);
        }

        boolean approve=true;
        for(YearLine yearLine: yearlyReport.getYearReport()){

            if(yearLine.getIs_expense()){
                int monthlyExpense=0;
                try{
                    monthlyExpense = monthlyExpenses.get(yearLine.getMonth());
                    //System.out.println("monthlyExpense: "+monthlyExpense);
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if(yearLine.getAmount()==monthlyExpense){
                    //System.out.println(true);
                    continue;
                }
                else{
                    //System.out.println(yearLine.getMonth());
                    //System.out.println(false);
                    approve=false;
                }
            }
            else{
                int monthlyIncome=0;
                try{
                    monthlyIncome = monthlyIncomes.get(yearLine.getMonth());
                    //System.out.println("monthlyIncome: "+monthlyIncome);
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if(yearLine.getAmount()==monthlyIncome){
                    //System.out.println(true);
                    continue;
                }
                else{
                    //System.out.println(yearLine.getMonth());
                    //System.out.println(false);
                    approve=false;
                }
            }
        }
        if(approve){
            System.out.println("Операция завершена успешно");
        }
    }

}

