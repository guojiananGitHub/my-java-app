package com.example;

import java.util.List;
import java.util.ArrayList;

public class App {

    // 潜在空指针问题
    public String greet(String name) {
        if (name.equals("World")) {
            return "Hello, World!";
        }
        return "Hello, " + name + "!";
    }

    // 资源未关闭问题
    public void readFile(String path) throws Exception {
        java.io.FileInputStream fis = new java.io.FileInputStream(path);
        int data = fis.read();
        // fis 未关闭
    }

    // 无用变量
    public int addNumbers(int a, int b) {
        int unusedVariable = 42;
        return a + b;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.greet("CI/CD"));
    }
}