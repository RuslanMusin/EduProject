import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Example {

    private static Market market = new Market();
    private static int JOB_COUNT_NORM = 30000; //предположительно самое большое число вакансий на данный момент.нужно для нормирования
    private static int INTEREST_NORM = 10; //макс. значение интереса
    private static int MARK_NORM = 100; //макс. значение оценки

    public static void main(String args[]) {
        //Образовательный граф и студент
        EduGraph graph = new EduGraph();
        Student student = new Student("Ruslan", graph);

        //Три начальных предмета
        Subject calcSubject = new Subject("Мат.анализ", 1);
        Subject algebraSubject = new Subject("Линейная алгебра", 1);
        Subject discretSubject = new Subject("Дискретная математика", 1);

        //Обязательный предмет за 2 семестр
        Subject algorithmSubject = new Subject("Алгоритмы и структуры данных", 2);

        //Пример требований к линейной алгебре
        Requirement algRecOne = new Requirement("Умножение матриц", algebraSubject);
        Requirement algRecTwo = new Requirement("Работа с комплексными числами", algebraSubject);
        algebraSubject.getRequirements().addAll(Arrays.asList(algRecOne, algRecTwo));

        //Пример работ на рынке и кол-во вакансий по ним.
        Job androidJob = new Job("Android разработчик", 3000);
        Job gameDevJob = new Job("Разработчик игр", 4000);
        Job javascriptDevJob = new Job("Javascript разработчик", 11000);
        market.getJobs().addAll(Arrays.asList(androidJob, gameDevJob, javascriptDevJob));

        //Предметы 3 курса
        Subject androidSubject = new Subject("Android разработка", 3);
        Subject javascriptSubject = new Subject("Разработка динамических сайтов", 3);
        Subject gameDevSubject = new Subject("Основы разработки игр", 3);

        Subject testSubject = new Subject("Основы тестирования", 5);

        //Требования к предметам и работам
        Requirement androidRecOne = new Requirement("Знание Kotlin", androidSubject);
        Requirement androidRecTwo = new Requirement("Знание Android SDK", androidSubject);
        Requirement androidRecThree = new Requirement("Понимание MVP", androidSubject);
        Requirement algoRecOne = new Requirement("Знание алгоритмов и структур данных", algorithmSubject);
        Requirement testRecOne = new Requirement("Умение писать Unit тесты", testSubject);
        androidJob.getRequirements().addAll(Arrays.asList(androidRecOne, androidRecTwo, androidRecThree, algoRecOne, testRecOne));
//        androidSubject.getRequirements().addAll(Arrays.asList(androidRecOne, androidRecTwo, androidRecThree));

        Requirement gameDevRecOne = new Requirement("Знание C#", gameDevSubject);
        Requirement gameDevRecTwo = new Requirement("Знание Unity", gameDevSubject);
        Requirement gameDevRecThree = new Requirement("Понимание MVP", gameDevSubject);
        gameDevJob.getRequirements().addAll(Arrays.asList(gameDevRecOne, gameDevRecTwo, gameDevRecThree, algoRecOne, testRecOne));
//        gameDevSubject.getRequirements().addAll(Arrays.asList(gameDevRecOne, gameDevRecTwo, gameDevRecThree));

        Requirement javascriptRecOne = new Requirement("Знание Javascript", javascriptSubject);
        Requirement javascriptRecTwo = new Requirement("Знание библиотек JS", javascriptSubject);
        Requirement javascriptRecThree = new Requirement("Понимание MVP", javascriptSubject);
        javascriptDevJob.getRequirements().addAll(Arrays.asList(javascriptRecOne, javascriptRecTwo, javascriptRecThree, algoRecOne, testRecOne));
//        javascriptSubject.getRequirements().addAll(Arrays.asList(javascriptRecOne, javascriptRecTwo, javascriptRecThree));

        //Ставим для предметов предшествующие предметы, которые нужно пройти
        algorithmSubject.getPastSubjects().addAll(Arrays.asList(algebraSubject, discretSubject, calcSubject));
        androidSubject.getPastSubjects().addAll(Arrays.asList(algebraSubject, discretSubject, algorithmSubject));
        javascriptSubject.getPastSubjects().addAll(Arrays.asList(algebraSubject, algorithmSubject));
        gameDevSubject.getPastSubjects().addAll(Arrays.asList(algebraSubject, discretSubject, calcSubject, algorithmSubject));

        //Составляем связь между студентом и предметом (ставим оценку (если пройден), интерес)
        StudentSubject studentAlgebra = new StudentSubject(72, 6, student, algebraSubject);
        StudentSubject studentCalc = new StudentSubject(81, 4, student, calcSubject);
        StudentSubject studentDiscretMath = new StudentSubject(69, 5, student, discretSubject);
        StudentSubject studentAlgo = new StudentSubject(8, student, algorithmSubject);
        StudentSubject studentAndroid = new StudentSubject(8, student, androidSubject);
        StudentSubject studentJavascript = new StudentSubject(4, student, javascriptSubject);
        StudentSubject studentGameDev = new StudentSubject(5, student, gameDevSubject);

        //Заполняем образовательный граф пройденными и возможными предметами.
        student.getEduGraph().getStartedSubjects().addAll(Arrays.asList(
           studentAlgebra, studentCalc, studentDiscretMath
        ));

        student.getEduGraph().getPossibleSubjects().addAll(Arrays.asList(
           studentAlgo
        ));

        //Студент переходит на второй семестр
        student.setSemestr(2);
        //Выбираем предмет исходя из требований. В данном случае выбора нет, так только Алгоритмы.
        StudentSubject sub = chooseNextSubject(student);
        System.out.println("Final Sub " + sub.getSubject().getTitle() + " with value = " + sub.getUserValue());

        //Меняем структуру графа после прохждения Алгоритмов
        student.getEduGraph().getStartedSubjects().add(studentAlgo);
        student.getEduGraph().getPossibleSubjects().remove(studentAlgo);
        student.getEduGraph().getPossibleSubjects().addAll(Arrays.asList(studentAndroid, studentJavascript, studentGameDev));
        studentAlgo.setMarkValue(84);

        //Студент переходит на третий семестр
        student.setSemestr(3);
        //Выбираем предмет исходя из требований. В данном случае выбор есть
        //Программой предлагается предмет с самым высоким коэффицентом
        sub = chooseNextSubject(student);
        System.out.println("Final Sub " + sub.getSubject().getTitle() + " with value = " + sub.getUserValue());
    }

    private static StudentSubject chooseNextSubject(Student student) {
        //Из возможных предметов берем все за текущий семестр
        List<StudentSubject> subjects = student.getEduGraph().getPossibleSubjects()
                .stream().filter(i -> i.getSubject().getSemestr().equals(student.getSemestr())).collect(Collectors.toList());
        System.out.println("possible subjects " + Arrays.toString(subjects.toArray()));

        for(StudentSubject subject: subjects) {
            // Интерес по предшествующим предметам (если нравилась лин.алгебра,
            // то может понравится и разработка Игр.
            double pastInterest = findPastSubjectsInterest(subject);
            // Оценка по предшествующим предметам (если хорошая оценка по лин.алгебра,
            // то может получится и с разработкой Игр.
            double pastMarks = findPastSubjectsMarks(subject);
            // Текущий возможный интерес к новому предмету,
            double currentInterest = Double.valueOf(subject.getInterestValue())/10;
            // Учитываем влияние рынка. Кол-во вакансий по работам, требования которых пересекаются
            // с тем, что может дать данный предмет (насколько имеет смысл изучать с точки зреиня рынка)
            double jobsValue = findJobsValue(subject);
            System.out.println("Subject " + subject.toString() + " part pastInterest = " + pastInterest);
            System.out.println("Subject " + subject.toString() + " part pastMarks = " + pastMarks);
            System.out.println("Subject " + subject.toString() + " part currentInterest = " + currentInterest);
            System.out.println("Subject " + subject.toString() + " part jobsValue = " + jobsValue);

            //Вычисляем конечный вес по возможному предмету, учитывая четыре фактора выше
            double value = findPastSubjectsInterest(subject)
                    + findPastSubjectsMarks(subject)
                    + Double.valueOf(subject.getInterestValue())/10
                    + findJobsValue(subject);
            subject.setUserValue(value);
            System.out.println("Subject " + subject.getSubject().getTitle() + " with value = " + subject.getUserValue());
        }
        //Возвращаем предмет с самым высоким коэффицентом.
        return subjects.stream().max((o1, o2) -> o1.getUserValue().compareTo(o2.getUserValue())).get();
    }

    private static Double findJobsValue(StudentSubject subject) {
        Integer sum = 0;
        for(Job job: findRelatedJobs(subject)) {
            sum += job.getCount();
        }
        return Double.valueOf(sum) / 30000;
    }

    private static List<Job> findRelatedJobs(StudentSubject subject) {
        List<Job> jobs = market.getJobs();
        List<Job> relatedJobs = new ArrayList<>();
        for(Requirement req: subject.getSubject().getRequirements()) {
            for(Job job: jobs) {
                if(job.getRequirements().contains(req) && !relatedJobs.contains(job)) {
                    relatedJobs.add(job);
                }
            }
        }
        System.out.println("related jobs " + Arrays.toString(relatedJobs.toArray()));
        return relatedJobs;
    }

    private static Double findPastSubjectsInterest(StudentSubject subject) {
        Integer sum = 0;
        for(Subject sub: subject.getSubject().getPastSubjects()) {
            sum += findRelatedSubject(sub, subject.getStudent()).getInterestValue();
        }
        return Double.valueOf(sum)/ (subject.getSubject().getPastSubjects().size() * 10);
    }

    private static Double findPastSubjectsMarks(StudentSubject subject) {
        Integer sum = 0;
        for(Subject sub: subject.getSubject().getPastSubjects()) {
            sum += findRelatedSubject(sub, subject.getStudent()).getMarkValue();
        }
        return Double.valueOf(sum)/ (subject.getSubject().getPastSubjects().size() * 100);
    }

    private static StudentSubject findRelatedSubject(Subject sub, Student student) {
        return student.getEduGraph().getStartedSubjects()
                .stream()
                .filter(i -> i.getSubject().equals(sub))
                .findFirst().get();
    }

}
