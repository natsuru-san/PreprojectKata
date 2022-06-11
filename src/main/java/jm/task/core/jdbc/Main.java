package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    //Користувачи
    private static final String[] names = {"Sotona", "Siska", "Volodya", "Valera"};
    private static final String[] lastNames = {"Atttsky", "Piva", "Putler", "Deagle"};
    private static final byte[] ages = {66, 2, 99, 7};
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            userService.saveUser(names[i], lastNames[i], ages[i]);
            System.out.println("Користувач з именем – " + names[i] + " доданий в базу даних");
        }
        userService.getAllUsers().forEach(u -> System.out.println(u.toString()));
        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}