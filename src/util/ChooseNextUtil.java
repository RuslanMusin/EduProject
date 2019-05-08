package util;

import entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseNextUtil {

    public static void chooseNextSubject(Student student, StudentSubject subject, Integer markValue) {
        subject.setMarkValue(markValue);
        student.getEduGraph().changeToStarted(subject);
        System.out.println("Choosed Subject " + subject.toString());
    }

    public static List<StudentSubject> getNextOrderedSubjects(Student student, String block) {
        //Из возможных предметов берем все за текущий семестр
        List<StudentSubject> subjects = student.getEduGraph().getPossibleSubjects(block)
                .stream().filter(i -> i.getSubject().getSemestr().equals(student.getSemestr())).collect(Collectors.toList());
        System.out.println("possible subjects " + Arrays.toString(subjects.toArray()));

        for(StudentSubject subject: subjects) {
            // Интерес по предшествующим предметам (если нравилась лин.алгебра,
            // то может понравится и разработка Игр.
            double pastInterest = findPastSubjectsInterest(subject, student);
            // Оценка по предшествующим предметам (если хорошая оценка по лин.алгебра,
            // то может получится и с разработкой Игр.
            double pastMarks = findPastSubjectsMarks(subject, student);
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
            double value = findPastSubjectsInterest(subject, student)
                    + findPastSubjectsMarks(subject, student)
                    + Double.valueOf(subject.getInterestValue())/ SimpleStorage.INTEREST_NORM
                    + findJobsValue(subject);
            subject.setUserValue(value);
        }
        subjects.sort((o1, o2) -> -o1.getUserValue().compareTo(o2.getUserValue()));
        System.out.println("subject in order " + Arrays.toString(subjects.toArray()));

        //Возвращаем предметы
        return subjects;
    }

    private static Double findJobsValue(StudentSubject subject) {
        Integer sum = 0;
        for(Job job: findRelatedJobs(subject)) {
            sum += job.getCount();
        }
        return Double.valueOf(sum) / SimpleStorage.JOB_COUNT_NORM;
    }

    private static List<Job> findRelatedJobs(StudentSubject subject) {
        List<Job> jobs = SimpleStorage.market.getJobs();
        List<Job> relatedJobs = new ArrayList<>();
//        System.out.println("jobs sub req" + Arrays.toString(subject.getSubject().getRequirements().toArray()));
        for(Requirement req: subject.getSubject().getRequirements()) {
            for(Job job: jobs) {
//                System.out.println("jobs req" + Arrays.toString(job.getRequirements().toArray()));
                if(job.getRequirements().contains(req) && !relatedJobs.contains(job)) {
                    relatedJobs.add(job);
                }
            }
        }
        System.out.println("related jobs " + Arrays.toString(relatedJobs.toArray()));
        return relatedJobs;
    }

    private static Double findPastSubjectsInterest(StudentSubject subject, Student student) {
        Integer sum = 0;
        for(Subject sub: subject.getSubject().getPastSubjects()) {
            sum += findRelatedSubject(sub, student).getInterestValue();
        }
        return Double.valueOf(sum)/ (subject.getSubject().getPastSubjects().size() * SimpleStorage.INTEREST_NORM);
    }

    private static Double findPastSubjectsMarks(StudentSubject subject, Student student) {
        Integer sum = 0;
        for(Subject sub: subject.getSubject().getPastSubjects()) {
            sum += findRelatedSubject(sub, student).getMarkValue();
        }
        return Double.valueOf(sum)/ (subject.getSubject().getPastSubjects().size() * SimpleStorage.MARK_NORM);
    }

    private static StudentSubject findRelatedSubject(Subject sub, Student student) {
      /*  System.out.println("started subjects " + Arrays.toString(student.getEduGraph().getStartedSubjects().toArray()));
        System.out.println("subject " + sub.toString());*/
        return student.getEduGraph().getStartedSubjects()
                .stream()
                .filter(i -> i.getSubject().equals(sub))
                .findFirst().get();
    }

}
