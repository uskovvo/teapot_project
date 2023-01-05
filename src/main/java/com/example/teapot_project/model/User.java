package com.example.teapot_project.model;

import com.example.teapot_project.exceptions.NotValidDataException;
import com.example.teapot_project.utils.StringValidator;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String surname;


    private int age;

    private Long groupId;

    private boolean answerStatus;


    public User() {
    }

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public User( String name, String surname, long groupId, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.groupId = groupId;
    }

    public String getGroup(List<Group> groups){
        for (Group group :groups) {
            if (group.getId() == groupId)  {
                return group.getGroupColor();
            }
        }
        return "none";
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public boolean isAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(boolean answerStatus) {
        this.answerStatus = answerStatus;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", groupId=" + groupId +
                ", answerStatus=" + answerStatus +
                '}';
    }

    public String toStringName(){
        return "" + name + " " + surname;
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
        if(age < 0 || age > 150)
            throw new NotValidDataException("Age must not be less than 0 and higher than 150");
        this.age = age;
    }
}
