package com.backend.rpgtask.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "habits")
public class HabitsEntity implements Serializable {

    private static final long serialVersionUID = 1444424857639983847L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String taskTitle;

    @Column(nullable = false, length = 500)
    private String taskText;

    @Column(nullable = false)
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }
}
