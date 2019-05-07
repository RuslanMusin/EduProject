package util;

import com.google.gson.Gson;
import entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleStorage {

    public static int JOB_COUNT_NORM = 30000; //предположительно самое большое число вакансий на данный момент.нужно для нормирования
    public static int INTEREST_NORM = 10; //макс. значение интереса
    public static int MARK_NORM = 100; //макс. значение оценки
    public static String FORMAT = ".dat";

    public static Random rand = new Random();

    public static String fieldStudent = "student";
    public static String fieldMarket = "market";
    public static String fieldEduStandart = "eduStandart";

    public static Student student;
    public static Market market = new Market();
    public static EduStandart eduStandart = new EduStandart();
//    public static List<entity.Requirement> requirements = new ArrayList<>();
    public static Gson gson = new Gson();

    public static List<Requirement> getRequirements() {
        List<Requirement> requirements = new ArrayList<>();
        for (Subject sub : SimpleStorage.eduStandart.getSubjects()) {
            requirements.addAll(sub.getRequirements());
        }
        return requirements;
    }

    private static void saveField(String fieldName, Object fieldValue) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(fieldName + FORMAT));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(fieldValue);
        oos.close();
    }


    private static Object readField(String fieldName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File( fieldName + FORMAT));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object value = ois.readObject();
        ois.close();

        return value;
    }

    public static void save() throws IOException {
        removeOldFiles();
        saveField(fieldStudent, gson.toJson(student));
        saveField(fieldMarket, gson.toJson(market));
        saveField(fieldEduStandart, gson.toJson(eduStandart));
    }

    public static void load() throws IOException, ClassNotFoundException {
        student = gson.fromJson((String)readField(fieldStudent), Student.class);
        market = gson.fromJson((String)readField(fieldMarket), Market.class);
        eduStandart = gson.fromJson((String)readField(fieldEduStandart), EduStandart.class);

    }

    public static void removeOldFiles() throws IOException {
        new File(fieldStudent + FORMAT).delete();
        new File(fieldMarket + FORMAT).delete();
        new File(fieldEduStandart + FORMAT).delete();
    }
}
