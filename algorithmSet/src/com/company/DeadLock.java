package com.company;

public class DeadLock implements Runnable{
    private String str1;
    private String str2;
    public DeadLock(String str1,String str2){
        this.str1 = str1;
        this.str2 = str2;
    }
    @Override
    public void run(){
        synchronized (str1){
            System.out.println(Thread.currentThread().getName()+"\tlock"+str1);
            try{
                Thread.currentThread().sleep(10);
                synchronized (str2){
                    System.out.println(Thread.currentThread().getName()+"\tlock"+str2);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        String str1 = "A";
        String str2 = "B";

        new Thread(new DeadLock(str1, str2), "Thread-A").start();
        new Thread(new DeadLock(str2, str1), "Thread-B").start();
    }
}
