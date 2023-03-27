package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import monthReport.*;
import yearReport.*;

public class Main  {
    private static Converter converter;
    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, ArrayList<MonthlyReport>> monthlyReports = new HashMap<>();
        HashMap<Integer, YearlyReport> yearlyReports = new HashMap<>();
        converter = new Converter();
        //Год отчетности
        int year = 2021;
        while(true){
            printMenu();
            String scan = scanner.next();
            switch(scan){
                case("1"):{
                    ArrayList<MonthlyReport> reports = new ArrayList<>();
                    for(String files: findFiles("m."+year)){
                        List<String> file = readFileContents(files);
                        reports.add(new MonthlyReport(file));
                    }
                    monthlyReports.put(year, reports);
                    break;
                }
                case("2"):{
                    YearlyReport report = null;
                    for(String files: findFiles("y."+year)){
                        List<String> file = readFileContents(files);
                        report = new YearlyReport(file);
                    }
                    yearlyReports.put(year, report);
                    break;
                }
                case("3"):{
                    if(monthlyReports.isEmpty()){
                        System.out.println("Отсутствуют месячные отчеты");
                    }
                    else if(yearlyReports.isEmpty()){
                        System.out.println("Отсутствует годовой отчет");
                        }
                        else{
                            reconciliation(monthlyReports.get(year),yearlyReports.get(year));
                        }
                    break;
                }
                case("4"):{
                    if(monthlyReports.isEmpty() || monthlyReports.get(year).size() == 0){
                        System.out.println("Отсутствуют месячные отчеты!");
                    }
                    else {
                        for (int i = 0; i < monthlyReports.get(year).size(); i++) {
                            String printMonth = converter.getNameMonth(i + 1);
                            System.out.println(printMonth.substring(0, 1).toUpperCase() + printMonth.substring(1));
                            monthlyReports.get(year).get(i).getReport();
                        }
                    }
                    break;
                }
                case("5"):{
                    if(yearlyReports.isEmpty()) {
                        System.out.println("Отсутствует годовой отчет!");
                    }
                    else{
                        System.out.printf("Отчет за год:%n");
                        yearlyReports.get(year).getReport();
                    }
                    break;
                }
                case("0"):{
                    System.out.println("До свидания!");
                    return;
                }
                default:{
                    System.out.println("Такой команды не существует!");
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
        //Флаг успешности сверки
        boolean approve=true;
        //Мапы для дохода и расхода <Месяц, сумма>
        HashMap<Integer, Integer> monthlyExpenses = new HashMap<>();
        HashMap<Integer, Integer> monthlyIncomes = new HashMap<>();
        //Заполнение мап
        for(int i=0; i<monthlyReports.size(); i++) {
            int income = monthlyReports.get(i).getSum(false);
            int expense = monthlyReports.get(i).getSum(true);
            monthlyIncomes.put((i+1), income);
            monthlyExpenses.put((i+1), expense);
        }
        if(yearlyReport.searchMonth(monthlyExpenses, true)){
            approve=false;
        }
        else if(yearlyReport.searchMonth(monthlyExpenses, true)){
            approve=false;
        }
        else {
            //Цикл сверки
            for (YearLine yearLine : yearlyReport.getYearReport()) {
                //Проверка типа записи Доход/Расход в годовом отчете
                HashSet<Integer> monthlyNumbers = new HashSet<>();
                monthlyNumbers.addAll(monthlyExpenses.keySet());
                if (!monthlyNumbers.contains(yearLine.getMonth())) {
                    System.out.println("Отсутствует запись о "
                            + converter.getExpensiveOrIncome(yearLine.getIs_expense()).toLowerCase() + "е"
                            + " за "
                            + converter.getNameMonth(yearLine.getMonth())
                            + " в месячном отчете или отсутствует отчет");
                    approve = false;
                    continue;
                }
                if (yearLine.getIs_expense()) {
                    int monthlyExpense = monthlyExpenses.get(yearLine.getMonth());
                    if (yearLine.getAmount() == monthlyExpense) {
                        continue;
                    } else {
                        System.out.println("В месяце: "
                                + converter.getNameMonth(yearLine.getMonth())
                                + "\nВ годовом отчете "
                                + converter.getExpensiveOrIncome(yearLine.getIs_expense()).toLowerCase()
                                + " составил: "
                                + yearLine.getAmount()
                                + ", а в месячном отчете: "
                                + monthlyExpense
                        );
                        approve = false;
                    }
                } else {
                    int monthlyIncome = monthlyIncomes.get(yearLine.getMonth());
                    if (yearLine.getAmount() == monthlyIncome) {
                        continue;
                    } else {
                        System.out.println("В месяце: "
                                + converter.getNameMonth(yearLine.getMonth())
                                + "\nВ годовом отчете "
                                + converter.getExpensiveOrIncome(yearLine.getIs_expense()).toLowerCase()
                                + " составил: "
                                + yearLine.getAmount()
                                + ", а в месячном отчете: "
                                + monthlyIncome
                        );
                        approve = false;
                    }
                }
            }
        }
        //Проверка флага прохождения сверки
        if(approve){
            System.out.println("Сверка пройдена успешно!");
        }
        else{
            System.out.println("Сверка не пройдена!");
        }
    }

}

