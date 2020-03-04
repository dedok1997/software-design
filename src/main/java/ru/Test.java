package ru;

public class Test {


    static void dos(int i ){

    }

    public static void main(String[] args){

        for(int i = 0;i<100000000;i++){
            if(i==10000000) {
                dos(i);
            }else{
                dos(i);
            }
        }
    }
}
