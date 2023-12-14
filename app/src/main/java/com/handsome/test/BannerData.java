package com.handsome.test;

import java.util.List;

public class BannerData {
    List<DetailData> data;
    int errorCode;
    String errorMsg;
    static class DetailData{
        String desc;
        int id ;
        String imagePath ;
        int isVisible ;
        int order ;
        String title ;
        int type ;
        String url;
    }
}