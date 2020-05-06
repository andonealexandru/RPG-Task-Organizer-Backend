package com.backend.rpgtask.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1217415876161501652L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private Boolean enabled = false;

    @Column(nullable = false)
    private String confirmationToken;

    @Column(nullable = false)
    private Integer money;

    @Column(nullable = false)
    private Integer level;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<ToDoEntity> toDoEntityList;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<HabitsEntity> habitsEntityList;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<DailyEntity> dailyEntityList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<ToDoEntity> getToDoEntityList() {
        return toDoEntityList;
    }

    public void setToDoEntityList(List<ToDoEntity> toDoEntityList) {
        this.toDoEntityList = toDoEntityList;
    }

    public List<HabitsEntity> getHabitsEntityList() {
        return habitsEntityList;
    }

    public void setHabitsEntityList(List<HabitsEntity> habitsEntityList) {
        this.habitsEntityList = habitsEntityList;
    }

    public List<DailyEntity> getDailyEntityList() {
        return dailyEntityList;
    }

    public void setDailyEntityList(List<DailyEntity> dailyEntityList) {
        this.dailyEntityList = dailyEntityList;
    }
}
