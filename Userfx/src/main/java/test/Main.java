package test;

 import entite.User;
 import service.UserService;
 import util.DataSource;

public class Main {
    public static void main(String[] args) {

        User u1 = new User("email@ew.com", "rdrr", "test2", "password123", "[\"ROLE_USER\"]");

        UserService us = new UserService();
        //us.add(u1);
        us.getAll().forEach(System.out::println);

    }
}
