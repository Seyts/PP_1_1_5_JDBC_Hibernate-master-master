package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        List<User> testList = new ArrayList<>();
        testList.add(new User("Иван", "Иванов", (byte) 25));
        testList.add(new User("Анна", "Петрова", (byte) 35));
        testList.add(new User("Дмитрий", "Сидоров", (byte) 42));
        testList.add(new User("Елена", "Кузнецова", (byte) 29));
        testList.forEach(x -> {
            userService.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.format("User с именем – %s добавлен в базу данных%n", x.getName());
        });
        userService.getAllUsers().forEach(user -> System.out.println(user.toString())); ;
        userService.cleanUsersTable();
        userService.dropUsersTable();





    }
}
