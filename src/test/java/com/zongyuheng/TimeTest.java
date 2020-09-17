package com.zongyuheng;

import java.text.SimpleDateFormat;

public class TimeTest {

        public static  final SimpleDateFormat fmt=new SimpleDateFormat("HH:mm:ss ,SSS");
        public  interface Task{
            void excute();
        }

        public  static  void getTime(String name,Task task){
            System.out.println(name+" "+fmt.format(System.currentTimeMillis()));
            task.excute();
            System.out.println(name+" "+fmt.format(System.currentTimeMillis()));





        }
}
