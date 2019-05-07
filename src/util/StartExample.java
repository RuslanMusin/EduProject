package util;

import entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.ChooseNextUtil.chooseNextSubject;


public class StartExample {

    public static void startData() {
        //Образовательный граф и студент
        EduGraph graph = new EduGraph();
        Student student = new Student("Ruslan", graph);
        SimpleStorage.student = student;
        //Три начальных предмета
        Subject calcSubject = new Subject("Мат.анализ", 1);
        Subject algebraSubject = new Subject("Линейная алгебра", 1);
        Subject discretSubject = new Subject("Дискретная математика", 1);

        //Обязательный предмет за 2 семестр
        Subject algorithmSubject = new Subject("Алгоритмы и структуры данных", 2);
        SimpleStorage.eduStandart.getSubjects().addAll(Arrays.asList(calcSubject, algebraSubject, discretSubject, algorithmSubject));

        //Пример требований к линейной алгебре
        Requirement algRecOne = new Requirement("Умножение матриц", algebraSubject);
        Requirement algRecTwo = new Requirement("Работа с комплексными числами", algebraSubject);

        //Пример работ на рынке и кол-во вакансий по ним.
        Job androidJob = new Job("Android разработчик", 3000);
        Job gameDevJob = new Job("Разработчик игр", 4000);
        Job javascriptDevJob = new Job("Javascript разработчик", 11000);
        SimpleStorage.market.getJobs().addAll(Arrays.asList(androidJob, gameDevJob, javascriptDevJob));

        //Предметы 3 курса
        Subject androidSubject = new Subject("Android разработка", 3);
        Subject javascriptSubject = new Subject("Разработка динамических сайтов", 3);
        Subject gameDevSubject = new Subject("Основы разработки игр", 3);

        Subject testSubject = new Subject("Основы тестирования", 5);
        SimpleStorage.eduStandart.getSubjects().addAll(Arrays.asList(androidSubject, javascriptSubject, gameDevSubject));

        //Требования к предметам и работам
        Requirement androidRecOne = new Requirement("Знание Kotlin", androidSubject);
        Requirement androidRecTwo = new Requirement("Знание Android SDK", androidSubject);
        Requirement androidRecThree = new Requirement("Понимание MVP", androidSubject);
        Requirement algoRecOne = new Requirement("Знание алгоритмов и структур данных", algorithmSubject);
        Requirement testRecOne = new Requirement("Умение писать Unit тесты", testSubject);
        androidJob.getRequirements().addAll(Arrays.asList(androidRecOne, androidRecTwo, androidRecThree, algoRecOne, testRecOne));

        Requirement gameDevRecOne = new Requirement("Знание C#", gameDevSubject);
        Requirement gameDevRecTwo = new Requirement("Знание Unity", gameDevSubject);
        Requirement gameDevRecThree = new Requirement("Понимание MVP", gameDevSubject);
        gameDevJob.getRequirements().addAll(Arrays.asList(gameDevRecOne, gameDevRecTwo, gameDevRecThree, algoRecOne, testRecOne));

        Requirement javascriptRecOne = new Requirement("Знание Javascript", javascriptSubject);
        Requirement javascriptRecTwo = new Requirement("Знание библиотек JS", javascriptSubject);
        Requirement javascriptRecThree = new Requirement("Понимание MVP", javascriptSubject);
        javascriptDevJob.getRequirements().addAll(Arrays.asList(javascriptRecOne, javascriptRecTwo, javascriptRecThree, algoRecOne, testRecOne));

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

      /*  student.getEduGraph().getPossibleSubjects().addAll(Arrays.asList(
           studentAlgo
        ));*/

        //Студент переходит на второй семестр
        student.setSemestr(2);
        //Выбираем предмет исходя из требований. В данном случае выбора нет, так только Алгоритмы.
        StudentSubject sub = chooseNextSubject(student);
        System.out.println("Final Sub " + sub.getSubject().getTitle() + " with value = " + sub.getUserValue());

        //Меняем структуру графа после прохждения Алгоритмов
        student.getEduGraph().changeToStarted(studentAlgo);
//        student.getEduGraph().getPossibleSubjects().addAll(Arrays.asList(studentAndroid, studentJavascript, studentGameDev));
        studentAlgo.setMarkValue(84);

        //Студент переходит на третий семестр
        student.setSemestr(3);
        //Выбираем предмет исходя из требований. В данном случае выбор есть
        //Программой предлагается предмет с самым высоким коэффицентом
        sub = chooseNextSubject(student);
        System.out.println("Final Sub " + sub.getSubject().getTitle() + " with value = " + sub.getUserValue());
    }

}
