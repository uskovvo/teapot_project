package model;

import exceptions.NotValidDataException;
import utils.StringValidator;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String surname;
    private int age;

    public User() {
    }

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id == null || id < 0){
            throw new NotValidDataException("Wrong id");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(StringValidator.nameValidation(name)) {
            this.name = name;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if(StringValidator.surnameValidation(surname)){
        this.surname = surname;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age <= 0 || age > 150)
            throw new NotValidDataException("Age must not be less than 0 and higher than 150");
        this.age = age;
    }
}
