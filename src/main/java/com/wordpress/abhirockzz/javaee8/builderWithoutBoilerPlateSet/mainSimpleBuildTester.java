package com.wordpress.abhirockzz.javaee8.builderWithoutBoilerPlateSet;

public class mainSimpleBuildTester {

    public static void main(String[] args) {
        SimpleBean.SimpleBeanBuilder simpleBeanBuilder = new SimpleBean.SimpleBeanBuilder();
//        SimpleBean pippo = simpleBeanBuilder.setIndex(1).setName("Pippo").build();
        SimpleBean pippo = simpleBeanBuilder.builder().setIndex(1).setName("Pippo").build();
        System.out.println(pippo);
    }
}
