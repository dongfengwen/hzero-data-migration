package com.example.demo;

import static io.vavr.API.*;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-12-24 21:20
 */
public class Vavr {

    int i = 1;
    String a = Match(i).of(
            Case($(1), "one"), // 等值匹配
            Case($(2), "two"),
            Case($(), "?")
    );
    

}
