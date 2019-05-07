import entity.*;
import util.SimpleStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static util.ChooseNextUtil.chooseNextSubject;
import static util.SimpleStorage.rand;

public class ExampleTwo {

    private static Scanner sc;


    public static void main(String args[]) throws IOException, ClassNotFoundException {
      /*  util.StartExample.startData();
        util.SimpleStorage.save();*/
        createNewPart();

    }

    private static void createNewPart() throws IOException, ClassNotFoundException {
        SimpleStorage.load();
        System.out.println("jobs " + Arrays.toString(SimpleStorage.market.getJobs().toArray()));
        sc = new Scanner(System.in);
        boolean flag = true;
        System.out.println("Выберите действие");
        int number;
        while(flag) {
            printMenu();
            if(sc.hasNextInt()) {
                number = sc.nextInt();
                sc.nextLine();
                if(number == 0) {
                    flag = false;
                    break;
                }
                chooseCommand(number);
                SimpleStorage.save();
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println(
                "1) Добавить предмет.\n" +
                "2) Добавить требование к предмету.\n" +
                "3) Добавить предшествующий предмет.\n" +
                "4) Создать связь между студентом и предметом.\n" +
                "5) Добавить работу.\n" +
                "6) Добавить требование к работе.\n" +
                "7) Перейти на новый семестр.\n" +
                "8) Рекомендовать выбор в новом семестре.\n"
        );
    }

    private static void addSubject() {
        String name = null;
        Integer semestr = null;

        System.out.println("Добавление предмета");
        System.out.println("Введите название");
        if(sc.hasNextLine()) {
            name = sc.nextLine();
        }
        System.out.println("Введите номер семестра");
        if(sc.hasNextInt()) {
            semestr = sc.nextInt();
            sc.nextLine();
        }
        if(semestr != null && name != null) {
            Subject sub = new Subject(name, semestr);
            SimpleStorage.eduStandart.getSubjects().add(sub);
            System.out.println("Предмет успешно добавлен!");
        }
    }

    private static void addRequirement() {
        String name = null;
        Integer subNum = null;
        System.out.println("Добавление требования");
        System.out.println("Введите название");
        if(sc.hasNextLine()) {
            name = sc.nextLine();
        }
        System.out.println("Выберите предмет из предложенных");
        int i = 1;
        for(Subject sub: SimpleStorage.eduStandart.getSubjects()) {
            System.out.println("" + i + ") " + sub.getTitle());
            i++;
        }
        if(sc.hasNextInt()) {
            subNum = sc.nextInt() - 1;
            sc.nextLine();
        }
        if(subNum != null && name != null) {
            Requirement rec = new Requirement(name,
                    SimpleStorage.eduStandart.getSubjects().get(subNum));
            System.out.println("Требование успешно добавлено!");
        }
    }

    private static void addSubjectBefore() {
        Integer subNumOne = null;
        Integer subNumTwo = null;
        System.out.println("Добавление предыдущего предмета");
        System.out.println("Введите новый предмет");
        printSubjects();
        if(sc.hasNextInt()) {
            subNumOne = sc.nextInt() - 1;
            sc.nextLine();
        }
        System.out.println("Выберите предшествующий предмет");
        printSubjectsBefore();
        if(sc.hasNextInt()) {
            subNumTwo = sc.nextInt() - 1;
            sc.nextLine();
        }
        if(subNumOne != null && subNumTwo != null) {
            SimpleStorage.eduStandart.getSubjects().get(subNumOne).getPastSubjects()
                    .add(SimpleStorage.eduStandart.getSubjects().get(subNumTwo));
            System.out.println("Связь между преметами установлена!");
        }
    }

    private static void bindStudentAndSubject() {
        Integer subNum = null;
        Integer interestPoint = null;
        System.out.println("Создание связи между студентом и предметом");
        System.out.println("Выберите предмет из предложенных");
        int i = 1;
        for(Subject sub: SimpleStorage.eduStandart.getSubjects()) {
            System.out.println("" + i + ") " + sub.getTitle());
            i++;
        }
        if(sc.hasNextInt()) {
            subNum = sc.nextInt() - 1;
            sc.nextLine();
        }
        System.out.println("Введите интерес к предмету от 1 до 10");
        if(sc.hasNextInt()) {
            interestPoint = sc.nextInt();
            sc.nextLine();
        }
        if(subNum != null && interestPoint != null) {
           StudentSubject studentSubject = new StudentSubject(interestPoint, SimpleStorage.student,
                    SimpleStorage.eduStandart.getSubjects().get(subNum));
            System.out.println("Связь между студентом и предметом успешно создана!");
        }
    }

    private static void updateSemestr() {
        System.out.println("Перейти на следующий семестр?\n" +
                "1) да\n" +
                "2) нет");
        if(sc.hasNextInt()) {
            int number = sc.nextInt();
            sc.nextLine();
            if(number == 1) {
                List<StudentSubject> list = SimpleStorage.student.getEduGraph().getPossibleSubjects()
                        .stream()
                        .filter(it -> it.getSubject().getSemestr() <= SimpleStorage.student.getSemestr())
                        .collect(Collectors.toList());
                for(StudentSubject sub: list) {
                    sub.setMarkValue(rand.nextInt(101-56) + 56);
                }
                SimpleStorage.student.getEduGraph().getStartedSubjects().addAll(
                        list
                );
                SimpleStorage.student.getEduGraph().getPossibleSubjects().removeAll(
                        list
                );
                SimpleStorage.student.setSemestr(SimpleStorage.student.getSemestr() + 1);
            }
        }
    }

    private static void addJob() {
        String name = null;
        Integer count = null;
        Integer subNum = null;
        System.out.println("Добавление работы");
        System.out.println("Введите название");
        if(sc.hasNextLine()) {
            name = sc.nextLine();
        }
        System.out.println("Введите кол-во вакансий");
        if(sc.hasNextInt()) {
            count = sc.nextInt();
            sc.nextLine();
        }
        if(name != null && count != null) {
            Job job = new Job(name, count);
            System.out.println("Выберите одно или несколько требований из предложенных");
            printRequirements();
            if(sc.hasNextLine()) {
                String line = sc.nextLine();
                Integer[] nums = (Integer[]) Arrays.stream(line.split(",")).map(Integer::valueOf).toArray();
                List<Requirement> requirements = new ArrayList<>();
                for(Integer num: nums) {
                    requirements.add(SimpleStorage.getRequirements().get(num));
                }
                job.getRequirements().addAll(requirements);
                SimpleStorage.market.getJobs().add(job);
                System.out.println("Работа успешно добавлена!");
            }
        }
    }

    private static void addJobRequirement() {
        Integer jobNum = null;
        System.out.println("Добавление требования к работе");
        System.out.println("Выберите работу");
        printJobs();
        if(sc.hasNextLine()) {
            jobNum = sc.nextInt();
            sc.nextLine();
        }
        if(jobNum != null) {
            System.out.println("Выберите одно или несколько требований из предложенных");
            printRequirements();
            if(sc.hasNextLine()) {
                String line = sc.nextLine();
                List<Integer> nums = Arrays.stream(line.split(",")).map(Integer::valueOf).collect(Collectors.toList());
                List<Requirement> requirements = new ArrayList<>();
                Job job = SimpleStorage.market.getJobs().get(jobNum - 1);
                for(Integer num: nums) {
                    Requirement rec = SimpleStorage.getRequirements().get(num - 1);
                    if(!job.getRequirements().contains(rec)) {
                        job.getRequirements().add(rec);
                    }
                }
                System.out.println("Требование к работе успешно добавлено!");
            }
        }
    }

    private static void printSubjects() {
        int i = 1;
        for(Subject sub: SimpleStorage.eduStandart.getSubjects()) {
            System.out.println("" + i + ") " + sub.getTitle());
            i++;
        }
    }

    private static void printSubjectsBefore() {
        int i = 1;
        List<Subject> subjects = SimpleStorage.eduStandart.getSubjects()
                .stream()
                .filter(it -> it.getSemestr() <= SimpleStorage.student.getSemestr())
                .collect(Collectors.toList());
        for(Subject sub: subjects) {
            System.out.println("" + i + ") " + sub.getTitle());
            i++;
        }
    }

    private static void printRequirements() {
        int i = 1;
        for(Subject sub: SimpleStorage.eduStandart.getSubjects()) {
            for(Requirement rec: sub.getRequirements()) {
                System.out.println("" + i + ") " + rec.getName() + " | Предмет " + sub.getTitle());
                i++;
            }
        }
    }

    private static void printJobs() {
        int i = 1;
        for(Job job: SimpleStorage.market.getJobs()) {
            System.out.println("" + i + ") " + job.getName());
            i++;
        }
    }

    private static void chooseCommand(int number) {
        switch (number) {

            case 1:  {
                addSubject();
                break;
            }

            case 2:  {
                addRequirement();
                break;
            }

            case 3:  {
                addSubjectBefore();
                break;
            }

            case 4:  {
                bindStudentAndSubject();
                break;
            }

            case 5:  {
                addJob();
                break;
            }

            case 6:  {
                addJobRequirement();
                break;
            }

            case 7:  {
                updateSemestr();
                break;
            }

            case 8:  {
                chooseNextSubject(SimpleStorage.student);
                break;
            }
        }

    }
}