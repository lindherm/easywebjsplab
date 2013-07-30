package com.watchdata.cardcheck.utils;

public class FaceThread extends Thread{


    public FaceThread() {
        super("facethread");
    }
    private FaceListener lis;

    public void addListener(FaceListener lis) {
        this.lis = lis;
    }

    public void run() {
        //TODO
        lis.UIOperate();
    }


}
