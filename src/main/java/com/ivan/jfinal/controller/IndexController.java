package com.ivan.jfinal.controller;

import com.jfinal.core.ActionKey;

/**
 * Created by feiFan.gou on 2017/8/14 15:27.
 */
public class IndexController extends BaseController {

    @ActionKey("/")
    public void index() {

        render("index.ftl");
//        Function<String,String> function =
    }
}
