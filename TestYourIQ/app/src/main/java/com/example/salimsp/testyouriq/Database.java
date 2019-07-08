package com.example.salimsp.testyouriq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database extends SQLiteOpenHelper {

    public static String data[][];
    public static String scoreData[][];

    SQLiteDatabase sqlDb;

    public static int DB_VER = 1;
    public static String DB = "Quiz";

    /*----------------Subject Tables Variable Declaration----------------------------*/

    public static String TBL_MathQtAns = "Math";
    public static String TBL_IQQtAns = "IQ";
    public static String TBL_PhysicsQtAns = "Physics";
    public static String TBL_EnglishQtAns = "English";
    public static String TBL_GKQtAns = "GK";

    public static String COL_Id = "Id";
    public static String COL_Question = "Question";
    public static String COL_Op1 = "Op1";
    public static String COL_Op2 = "Op2";
    public static String COL_Op3 = "Op3";
    public static String COL_Op4 = "Op4";

    /*----------------Score Board Table Variable Declaration----------------------------*/

    public static String TBL_ScoreBoard= "ScoreBoard";

    public static String COL_Username = "Username";
    public static String COL_Subject = "Subject";
    public static String COL_Score = "Score";
    public static String COL_Time = "Time";


    public Database(Context context){

        super(context,DB,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String MathQuery = "CREATE TABLE "+TBL_MathQtAns+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Question+" TEXT, "
                +COL_Op1+" TEXT, "
                +COL_Op2+" TEXT, "
                +COL_Op3+" TEXT, "
                +COL_Op4+" TEXT"
                +")";

        Log.i("Math Query:",""+MathQuery);
        db.execSQL(MathQuery);

        String PhysicsQuery = "CREATE TABLE "+TBL_PhysicsQtAns+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Question+" TEXT, "
                +COL_Op1+" TEXT, "
                +COL_Op2+" TEXT, "
                +COL_Op3+" TEXT, "
                +COL_Op4+" TEXT"
                +")";

        Log.i("Physics Query",PhysicsQuery);
        db.execSQL(PhysicsQuery);

        String EnglishQuery = "CREATE TABLE "+TBL_EnglishQtAns+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Question+" TEXT, "
                +COL_Op1+" TEXT, "
                +COL_Op2+" TEXT, "
                +COL_Op3+" TEXT, "
                +COL_Op4+" TEXT"
                +")";

        Log.i("English Query:",EnglishQuery);
        db.execSQL(EnglishQuery);
        String GKQuery = "CREATE TABLE "+TBL_GKQtAns+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Question+" TEXT, "
                +COL_Op1+" TEXT, "
                +COL_Op2+" TEXT, "
                +COL_Op3+" TEXT, "
                +COL_Op4+" TEXT"
                +")";

        Log.i("GK Query: ",GKQuery);
        db.execSQL(GKQuery);
        String IQQuery = "CREATE TABLE "+TBL_IQQtAns+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Question+" TEXT, "
                +COL_Op1+" TEXT, "
                +COL_Op2+" TEXT, "
                +COL_Op3+" TEXT, "
                +COL_Op4+" TEXT"
                +")";

        Log.i("IQ Query: ",IQQuery);
        db.execSQL(IQQuery);


        String  queryScoreBoard = "CREATE TABLE "+TBL_ScoreBoard+" ("
                +COL_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_Username+" TEXT, "
                +COL_Subject+" TEXT, "
                +COL_Score+" INTEGER, "
                +COL_Time+" TEXT "
                +")";

        Log.i("Query ScoreBoard: ",queryScoreBoard);
        db.execSQL(queryScoreBoard);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TBL_MathQtAns);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_EnglishQtAns);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_GKQtAns);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_IQQtAns);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_PhysicsQtAns);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_ScoreBoard);
        onCreate(db);
    }

    public void getQtAns(String TBL){

        sqlDb = this.getReadableDatabase();

        String col [] = {COL_Id,COL_Question,COL_Op1,COL_Op2,COL_Op3,COL_Op4};

        Cursor c1 = sqlDb.query(TBL,col,null,null,null,null,null);

        if (c1.getCount() == 0) {

            insertData();
        }

        Log.i("c1",c1.getCount()+"");

        Cursor c = sqlDb.query(TBL,col,null,null,null,null,null);

        Log.i("c",c.getCount()+"");

        data = new String[c.getCount()][5];

        int qtAns = c.getColumnIndex(COL_Question);
        int op1 = c.getColumnIndex(COL_Op1);
        int op2 = c.getColumnIndex(COL_Op2);
        int op3 = c.getColumnIndex(COL_Op3);
        int op4 = c.getColumnIndex(COL_Op4);
        int i = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            Database.data[i][0] = c.getString(qtAns);
            Database.data[i][1] = c.getString(op1);
            Database.data[i][2] = c.getString(op2);
            Database.data[i][3] = c.getString(op3);
            Database.data[i][4] = c.getString(op4);
            i++;
        }

        c.close();

        Log.i("Database i++",i+"");
        Log.i("Database.data",Database.data.length+"");

    }



    public void insertScore(String username, String subject, int score, String time){

        sqlDb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_Username,username);
        cv.put(COL_Subject, subject);
        cv.put(COL_Score, score);
        cv.put(COL_Time, time);

        sqlDb.insert(TBL_ScoreBoard,null,cv);

    }

    public void getScore(){

        sqlDb = this.getReadableDatabase();

        String col [] = {COL_Id,COL_Username,COL_Subject,COL_Score,COL_Time};

        Cursor c1 = sqlDb.rawQuery("SELECT * FROM "+TBL_ScoreBoard+" ORDER BY "+COL_Score+" DESC ", null);

        int count = c1.getCount();

        if(count>10){
            count = 10;
        }

        scoreData = new String[count][4];

        Cursor c = sqlDb.rawQuery("SELECT * FROM "+TBL_ScoreBoard+" ORDER BY "+COL_Score+" DESC LIMIT "+count, null);

        int username = c.getColumnIndex(COL_Username);
        int subject = c.getColumnIndex(COL_Subject);
        int score = c.getColumnIndex(COL_Score);
        int time = c.getColumnIndex(COL_Time);

        int i = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            Database.scoreData[i][0] = c.getString(username);
            Database.scoreData[i][1] = c.getString(subject);
            Database.scoreData[i][2] = c.getString(score);
            Database.scoreData[i][3] = c.getString(time);
            i++;
        }

        c.close();

        //Log.i("Database score i++",i+"");
        Log.i("Database.scoreData",Database.scoreData.length+"");
       }

    public void deleteScore(){

        sqlDb = getWritableDatabase();

        sqlDb.delete(TBL_ScoreBoard,null,null);

    }


    public void insertData(){

        /********************Math  Question Insertion*******************/

        sqlDb = this.getWritableDatabase();

        ContentValues MathCv1 = new ContentValues();

        MathCv1.put(COL_Question, "19 + ____ = 42");
        MathCv1.put(COL_Op1, "22");
        MathCv1.put(COL_Op2, "23");
        MathCv1.put(COL_Op3, "24");
        MathCv1.put(COL_Op4, "23");

        sqlDb.insert(TBL_MathQtAns,null,MathCv1);

        ContentValues MathCv2 = new ContentValues();

        MathCv2.put(COL_Question, "What is the symbol of pi?");
        MathCv2.put(COL_Op1, "€");
        MathCv2.put(COL_Op2, "π");
        MathCv2.put(COL_Op3, "Ω");
        MathCv2.put(COL_Op4, "π");

        sqlDb.insert(TBL_MathQtAns,null,MathCv2);

        ContentValues MathCv3 = new ContentValues();

        MathCv3.put(COL_Question, "Arrange the numbers in ascending order: 36, 12, 29, 21, 7");
        MathCv3.put(COL_Op1, "36, 29, 21, 12, 7");
        MathCv3.put(COL_Op2, "7, 12, 21, 29, 36");
        MathCv3.put(COL_Op3, "None of these");
        MathCv3.put(COL_Op4, "7, 12, 21, 29, 36");

        sqlDb.insert(TBL_MathQtAns,null,MathCv3);

        ContentValues MathCv4 = new ContentValues();

        MathCv4.put(COL_Question, "What is the greatest two digit number?");
        MathCv4.put(COL_Op1, "10");
        MathCv4.put(COL_Op2, "90");
        MathCv4.put(COL_Op3, "99");
        MathCv4.put(COL_Op4, "99");

        sqlDb.insert(TBL_MathQtAns,null,MathCv4);
        ContentValues MathCv5 = new ContentValues();

        MathCv5.put(COL_Question, "How much is 90 – 19?");
        MathCv5.put(COL_Op1, "79");
        MathCv5.put(COL_Op2, "71");
        MathCv5.put(COL_Op3, "89");
        MathCv5.put(COL_Op4, "71");

        sqlDb.insert(TBL_MathQtAns,null,MathCv5);

        ContentValues MathCv6 = new ContentValues();

        MathCv6.put(COL_Question, "20 is divisible by ______");
        MathCv6.put(COL_Op1, "1");
        MathCv6.put(COL_Op2, "3");
        MathCv6.put(COL_Op3, "7");
        MathCv6.put(COL_Op4, "1");

        sqlDb.insert(TBL_MathQtAns,null,MathCv6);

        ContentValues MathCv7 = new ContentValues();

        MathCv7.put(COL_Question, "Find the value of x; if x = (2 × 3) + 11");
        MathCv7.put(COL_Op1, "55");
        MathCv7.put(COL_Op2, "192");
        MathCv7.put(COL_Op3, "17");
        MathCv7.put(COL_Op4, "17");

        sqlDb.insert(TBL_MathQtAns,null,MathCv7);

        ContentValues MathCv8 = new ContentValues();

        MathCv8.put(COL_Question, "What is the smallest three digit number?");
        MathCv8.put(COL_Op1, "100");
        MathCv8.put(COL_Op2, "120");
        MathCv8.put(COL_Op3, "130");
        MathCv8.put(COL_Op4, "100");

        sqlDb.insert(TBL_MathQtAns,null,MathCv8);

        ContentValues MathCv9 = new ContentValues();

        MathCv9.put(COL_Question, "How much is 190 – 87 + 16?");
        MathCv9.put(COL_Op1, "119");
        MathCv9.put(COL_Op2, "203");
        MathCv9.put(COL_Op3, "167");
        MathCv9.put(COL_Op4, "119");

        sqlDb.insert(TBL_MathQtAns,null,MathCv9);

        ContentValues MathCv10 = new ContentValues();

        MathCv10.put(COL_Question, "What is 1000 × 1 equal to?");
        MathCv10.put(COL_Op1, "1000");
        MathCv10.put(COL_Op2, "0");
        MathCv10.put(COL_Op3, "1");
        MathCv10.put(COL_Op4, "10000");

        sqlDb.insert(TBL_MathQtAns,null,MathCv10);


        /********************IQ  Question Insertion*******************/

        ContentValues IQCv1 = new ContentValues();

        IQCv1.put(COL_Question, "Book is to reading as fork is to.");
        IQCv1.put(COL_Op1, "Drawing");
        IQCv1.put(COL_Op2, "Writing");
        IQCv1.put(COL_Op3, "Eating");
        IQCv1.put(COL_Op4, "Eating");

        sqlDb.insert(TBL_IQQtAns,null,IQCv1);

        ContentValues IQCv2 = new ContentValues();

        IQCv2.put(COL_Question, "What is the missing number in the following sequence? 1 - 8 - 27 - ? - 125 - 216");
        IQCv2.put(COL_Op1, "64");
        IQCv2.put(COL_Op2, "36");
        IQCv2.put(COL_Op3, "46");
        IQCv2.put(COL_Op4, "64");

        sqlDb.insert(TBL_IQQtAns,null,IQCv2);

        ContentValues IQCv3 = new ContentValues();

        IQCv3.put(COL_Question, "Which number should come next: 37, 34, 31, 28, ?");
        IQCv3.put(COL_Op1, "26");
        IQCv3.put(COL_Op2, "25");
        IQCv3.put(COL_Op3, "22");
        IQCv3.put(COL_Op4, "25");

        sqlDb.insert(TBL_IQQtAns,null,IQCv3);

        ContentValues IQCv4 = new ContentValues();

        IQCv4.put(COL_Question, "Choose the word most similar to \"trustworthy\":");
        IQCv4.put(COL_Op1, "Resolute");
        IQCv4.put(COL_Op2, "Tenacity");
        IQCv4.put(COL_Op3, "Reliable");
        IQCv4.put(COL_Op4, "Reliable");

        sqlDb.insert(TBL_IQQtAns,null,IQCv4);

        ContentValues IQCv5 = new ContentValues();

        IQCv5.put(COL_Question, "Group A: talkative, job, ecstatic. Group B: angry, wind, loquacious. Which words from each group go together?");
        IQCv5.put(COL_Op1, "Talkative and wind");
        IQCv5.put(COL_Op2, "Talkative and loquacious");
        IQCv5.put(COL_Op3, "Job and angry");
        IQCv5.put(COL_Op4, "Talkative and loquacious");

        sqlDb.insert(TBL_IQQtAns,null,IQCv5);

        ContentValues IQCv6 = new ContentValues();

        IQCv6.put(COL_Question, "Which one of the following things is the least like the others?");
        IQCv6.put(COL_Op1, "Poem");
        IQCv6.put(COL_Op2, "Flower");
        IQCv6.put(COL_Op3, "Painting");
        IQCv6.put(COL_Op4, "Flower");

        sqlDb.insert(TBL_IQQtAns,null,IQCv6);

        ContentValues IQCv7 = new ContentValues();

        IQCv7.put(COL_Question, "What number best completes the analogy: 8:4 as 10:?");
        IQCv7.put(COL_Op1, "3");
        IQCv7.put(COL_Op2, "5");
        IQCv7.put(COL_Op3, "8");
        IQCv7.put(COL_Op4, "3");

        sqlDb.insert(TBL_IQQtAns,null,IQCv7);

        ContentValues IQCv8 = new ContentValues();

        IQCv8.put(COL_Question, "If you rearrange the letters \"CIFAIPC\" you would have the name of a(n):");
        IQCv8.put(COL_Op1, "City");
        IQCv8.put(COL_Op2, "Animal");
        IQCv8.put(COL_Op3, "Ocean");
        IQCv8.put(COL_Op4, "Ocean");

        sqlDb.insert(TBL_IQQtAns,null,IQCv8);

        ContentValues IQCv9 = new ContentValues();

        IQCv9.put(COL_Question, "Which of the following can be arranged into a 5-letter English word?");
        IQCv9.put(COL_Op1, "HRGST");
        IQCv9.put(COL_Op2, "RILSA");
        IQCv9.put(COL_Op3, "XTMZ");
        IQCv9.put(COL_Op4, "RILSA");

        sqlDb.insert(TBL_IQQtAns,null,IQCv9);

        ContentValues IQCv10 = new ContentValues();

        IQCv10.put(COL_Question, "If you rearrange the letters 'rengiai' you get the name of a/an:");
        IQCv10.put(COL_Op1, "Country");
        IQCv10.put(COL_Op2, "Animal");
        IQCv10.put(COL_Op3, "City");
        IQCv10.put(COL_Op4, "Country");

        sqlDb.insert(TBL_IQQtAns,null,IQCv10);

        /********************Physics Question Insertion*******************/

        ContentValues PhysicsCv1 = new ContentValues();

        PhysicsCv1.put(COL_Question, "Radiocarbon is produced in the atmosphere as a result of");
        PhysicsCv1.put(COL_Op1, "collision between fast neutrons and nitrogen nuclei present in the atmosphere");
        PhysicsCv1.put(COL_Op2, "action of ultraviolet light from the sun on atmospheric oxygen");
        PhysicsCv1.put(COL_Op3, "action of solar radiations particularly cosmic rays on carbon dioxide present in the atmosphere");
        PhysicsCv1.put(COL_Op4, "collision between fast neutrons and nitrogen nuclei present in the atmosphere");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv1);

        ContentValues PhysicsCv2 = new ContentValues();

        PhysicsCv2.put(COL_Question, "It is easier to roll a stone up a sloping road than to lift it vertical upwards because");
        PhysicsCv2.put(COL_Op1, "work done in rolling is more than in lifting");
        PhysicsCv2.put(COL_Op2, "work done in lifting the stone is equal to rolling it");
        PhysicsCv2.put(COL_Op3, "work done in rolling a stone is less than in lifting it");
        PhysicsCv2.put(COL_Op4, "work done in rolling a stone is less than in lifting it");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv2);

        ContentValues PhysicsCv3 = new ContentValues();

        PhysicsCv3.put(COL_Question, "The absorption of ink by blotting paper involves");
        PhysicsCv3.put(COL_Op1, "Viscosity of ink");
        PhysicsCv3.put(COL_Op2, "Capillary action phenomenon");
        PhysicsCv3.put(COL_Op3, "Diffusion of ink through the blotting");
        PhysicsCv3.put(COL_Op4, "Capillary action phenomenon");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv3);

        ContentValues PhysicsCv4 = new ContentValues();

        PhysicsCv4.put(COL_Question, "Siphon will fail to work if");
        PhysicsCv4.put(COL_Op1, "The densities of the liquid in the two vessels are equal");
        PhysicsCv4.put(COL_Op2, "The level of the liquid in the two vessels are at the same height");
        PhysicsCv4.put(COL_Op3, "Both its limbs are of unequal length");
        PhysicsCv4.put(COL_Op4, "The level of the liquid in the two vessels are at the same height");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv4);
        ContentValues PhysicsCv5 = new ContentValues();

        PhysicsCv5.put(COL_Question, "Large transformers, when used for some time, become very hot and are cooled by circulating oil. The heating of the transformer is due to");
        PhysicsCv5.put(COL_Op1, "the heating effect of current alone");
        PhysicsCv5.put(COL_Op2, "hysteresis loss alone");
        PhysicsCv5.put(COL_Op3, "both the heating effect of current and hysteresis loss");
        PhysicsCv5.put(COL_Op4, "both the heating effect of current and hysteresis loss");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv5);

        ContentValues PhysicsCv6 = new ContentValues();

        PhysicsCv6.put(COL_Question, "Nuclear sizes are expressed in a unit named");
        PhysicsCv6.put(COL_Op1, "Fermi");
        PhysicsCv6.put(COL_Op2, "angstrom");
        PhysicsCv6.put(COL_Op3, "newton");
        PhysicsCv6.put(COL_Op4, "Fermi");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv6);

        ContentValues PhysicsCv7 = new ContentValues();

        PhysicsCv7.put(COL_Question, "Light year is a unit of");
        PhysicsCv7.put(COL_Op1, "time");
        PhysicsCv7.put(COL_Op2, "distance");
        PhysicsCv7.put(COL_Op3, "light");
        PhysicsCv7.put(COL_Op4, "distance");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv7);

        ContentValues PhysicsCv8 = new ContentValues();

        PhysicsCv8.put(COL_Question, "Mirage is due to");
        PhysicsCv8.put(COL_Op1, "unequal heating of different parts of the atmosphere");
        PhysicsCv8.put(COL_Op2, "magnetic disturbances in the atmosphere");
        PhysicsCv8.put(COL_Op3, "equal heating of different parts of the atmosphere");
        PhysicsCv8.put(COL_Op4, "unequal heating of different parts of the atmosphere");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv8);

        ContentValues PhysicsCv9 = new ContentValues();

        PhysicsCv9.put(COL_Question, "Light from the Sun reaches us in nearly");
        PhysicsCv9.put(COL_Op1, "2 minutes");
        PhysicsCv9.put(COL_Op2, "4 minutes");
        PhysicsCv9.put(COL_Op3, "8 minutes");
        PhysicsCv9.put(COL_Op4, "8 minutes");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv9);

        ContentValues PhysicsCv10 = new ContentValues();

        PhysicsCv10.put(COL_Question, "Stars appears to move from east to west because");
        PhysicsCv10.put(COL_Op1, "all stars move from east to west");
        PhysicsCv10.put(COL_Op2, "the earth rotates from west to east");
        PhysicsCv10.put(COL_Op3, "the background of the stars moves from west to east");
        PhysicsCv10.put(COL_Op4, "the earth rotates from west to east");

        sqlDb.insert(TBL_PhysicsQtAns,null,PhysicsCv10);

        /********************English Question Insertion*******************/

        ContentValues EnglishCv1 = new ContentValues();

        EnglishCv1.put(COL_Question, "I told you ______ you didn't listen.");
        EnglishCv1.put(COL_Op1, "because");
        EnglishCv1.put(COL_Op2, "but");
        EnglishCv1.put(COL_Op3, "for");
        EnglishCv1.put(COL_Op4, "but");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv1);

        ContentValues EnglishCv2 = new ContentValues();

        EnglishCv2.put(COL_Question, "Hello, how are you?");
        EnglishCv2.put(COL_Op1, "Oh man, I's beat!");
        EnglishCv2.put(COL_Op2, "I'm fine, thanks.");
        EnglishCv2.put(COL_Op3, "I am doing finely.");
        EnglishCv2.put(COL_Op4, "I'm fine, thanks.");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv2);

        ContentValues EnglishCv3 = new ContentValues();

        EnglishCv3.put(COL_Question, "The soldiers ______ all the villagers.");
        EnglishCv3.put(COL_Op1, "listened");
        EnglishCv3.put(COL_Op2, "killed");
        EnglishCv3.put(COL_Op3, "waited");
        EnglishCv3.put(COL_Op4, "killed");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv3);

        ContentValues EnglishCv4 = new ContentValues();

        EnglishCv4.put(COL_Question, "What have you done?");
        EnglishCv4.put(COL_Op1, "I have done anything.");
        EnglishCv4.put(COL_Op2, "I've made a mess.");
        EnglishCv4.put(COL_Op3, "I've finished the job.");
        EnglishCv4.put(COL_Op4, "I've finished the job.");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv4);
        ContentValues EnglishCv5 = new ContentValues();

        EnglishCv5.put(COL_Question, "______ is your house?");
        EnglishCv5.put(COL_Op1, "When");
        EnglishCv5.put(COL_Op2, "Which");
        EnglishCv5.put(COL_Op3, "Who");
        EnglishCv5.put(COL_Op4, "Which");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv5);

        ContentValues EnglishCv6 = new ContentValues();

        EnglishCv6.put(COL_Question, "He was invited ______ he did not come.");
        EnglishCv6.put(COL_Op1, "if");
        EnglishCv6.put(COL_Op2, "but");
        EnglishCv6.put(COL_Op3, "when");
        EnglishCv6.put(COL_Op4, "but");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv6);

        ContentValues EnglishCv7 = new ContentValues();

        EnglishCv7.put(COL_Question, "Do they ever arrive on time?");
        EnglishCv7.put(COL_Op1, "No, they dont.");
        EnglishCv7.put(COL_Op2, "Yes, they usually does.");
        EnglishCv7.put(COL_Op3, "Yes, occasionally they do.");
        EnglishCv7.put(COL_Op4, "Yes, occasionally they do.");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv7);

        ContentValues EnglishCv8 = new ContentValues();

        EnglishCv8.put(COL_Question, "Last Sunday they ______ to the football game");
        EnglishCv8.put(COL_Op1, "went");
        EnglishCv8.put(COL_Op2, "go");
        EnglishCv8.put(COL_Op3, "gone");
        EnglishCv8.put(COL_Op4, "went");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv8);

        ContentValues EnglishCv9 = new ContentValues();

        EnglishCv9.put(COL_Question, "Never look directly ______ the sun. It is bad for your eyes.");
        EnglishCv9.put(COL_Op1, "to");
        EnglishCv9.put(COL_Op2, "through");
        EnglishCv9.put(COL_Op3, "at");
        EnglishCv9.put(COL_Op4, "at");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv9);

        ContentValues EnglishCv10 = new ContentValues();

        EnglishCv10.put(COL_Question, "I will speak ______ Suzanne when I see her.");
        EnglishCv10.put(COL_Op1, "in");
        EnglishCv10.put(COL_Op2, "toward");
        EnglishCv10.put(COL_Op3, "to");
        EnglishCv10.put(COL_Op4, "to");

        sqlDb.insert(TBL_EnglishQtAns,null,EnglishCv10);


        /********************GKQuestion Insertion*******************/

        ContentValues GKCv1 = new ContentValues();

        GKCv1.put(COL_Question, "Under article 61 of the UN Charter, the membership of the Economic and Social Council was which of the following years from 27 to 54?");
        GKCv1.put(COL_Op1, "1973");
        GKCv1.put(COL_Op2, "1983");
        GKCv1.put(COL_Op3, "1993");
        GKCv1.put(COL_Op4, "1973");

        sqlDb.insert(TBL_GKQtAns,null,GKCv1);

        ContentValues GKCv2 = new ContentValues();

        GKCv2.put(COL_Question, "NESCO (United Nations Education, Scientific and Cultural Organisation) was established in.");
        GKCv2.put(COL_Op1, "1919");
        GKCv2.put(COL_Op2, "1935");
        GKCv2.put(COL_Op3, "1946");
        GKCv2.put(COL_Op4, "1946");

        sqlDb.insert(TBL_GKQtAns,null,GKCv2);

        ContentValues GKCv3 = new ContentValues();

        GKCv3.put(COL_Question, "The term used to describe a sudden fall of a government, brought about by illegal force is called");
        GKCv3.put(COL_Op1, "credit squeeze");
        GKCv3.put(COL_Op2, "loup de' etat");
        GKCv3.put(COL_Op3, "deficit financing");
        GKCv3.put(COL_Op4, "loup de' etat");

        sqlDb.insert(TBL_GKQtAns,null,GKCv3);

        ContentValues GKCv4 = new ContentValues();

        GKCv4.put(COL_Question, "The victories of Karikala are well portrayed in");
        GKCv4.put(COL_Op1, "Aruvanad");
        GKCv4.put(COL_Op2, "Pattinappalai");
        GKCv4.put(COL_Op3, "Padirrupattu");
        GKCv4.put(COL_Op4, "Pattinappalai");

        sqlDb.insert(TBL_GKQtAns,null,GKCv4);
        ContentValues GKCv5 = new ContentValues();

        GKCv5.put(COL_Question, "Chemical formula for water is");
        GKCv5.put(COL_Op1, "NaAlO2");
        GKCv5.put(COL_Op2, "H2O");
        GKCv5.put(COL_Op3, "CaSiO3");
        GKCv5.put(COL_Op4, "H2O");

        sqlDb.insert(TBL_GKQtAns,null,GKCv5);

        ContentValues GKCv6 = new ContentValues();

        GKCv6.put(COL_Question, "What is VCM?");
        GKCv6.put(COL_Op1, "Virtual Channel Memory");
        GKCv6.put(COL_Op2, "Virtual Connection Manager");
        GKCv6.put(COL_Op3, "Voice Controlled Modem");
        GKCv6.put(COL_Op4, "Virtual Channel Memory");

        sqlDb.insert(TBL_GKQtAns,null,GKCv6);

        ContentValues GKCv7 = new ContentValues();

        GKCv7.put(COL_Question, "Mohiniattam dance from developed originally in which state?");
        GKCv7.put(COL_Op1, "Tamil Nadu");
        GKCv7.put(COL_Op2, "Orissa");
        GKCv7.put(COL_Op3, "Kerala");
        GKCv7.put(COL_Op4, "Kerala");

        sqlDb.insert(TBL_GKQtAns,null,GKCv7);

        ContentValues GKCv8 = new ContentValues();

        GKCv8.put(COL_Question, "The planets called the inner planets, are");
        GKCv8.put(COL_Op1, "Mercury, Venus, Earth, Mars");
        GKCv8.put(COL_Op2, "Jupiter, Saturn, Uranus, Neptune");
        GKCv8.put(COL_Op3, "Saturn, Uranus, Neptune, Pluto");
        GKCv8.put(COL_Op4, "Mercury, Venus, Earth, Mars");

        sqlDb.insert(TBL_GKQtAns,null,GKCv8);

        ContentValues GKCv9 = new ContentValues();

        GKCv9.put(COL_Question, "Without ____ the equator would be much hotter than it is while the poles would be much cooler.");
        GKCv9.put(COL_Op1, "cycle of air circulation");
        GKCv9.put(COL_Op2, "latitudinal redistribution of heat");
        GKCv9.put(COL_Op3, "All are similar terms");
        GKCv9.put(COL_Op4, "All are similar terms");

        sqlDb.insert(TBL_GKQtAns,null,GKCv9);

        ContentValues GKCv10 = new ContentValues();

        GKCv10.put(COL_Question, "Small amounts of iodine are necessary in our diet to");
        GKCv10.put(COL_Op1, "prevent pellagra");
        GKCv10.put(COL_Op2, "compensate for underactive the thyroid gland");
        GKCv10.put(COL_Op3, "stimulate clotting of blood\tstimulate pituitary gland");
        GKCv10.put(COL_Op4, "compensate for underactive the thyroid gland");

        sqlDb.insert(TBL_GKQtAns,null,GKCv10);


    }

}
