package cn.org.mrliu.http.entity.test;

import java.util.Arrays;
import java.util.List;

/**
 * @author MrLiu
 * @date 2016/12/17
 */
public class Account {
    private Integer id;
    private String name;
    private String pwd;
    private Account account;
    private String[] colors;
    private List<Double> scores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", account=" + account +
                ", colors=" + Arrays.toString(colors) +
                ", scores=" + scores +
                '}';
    }
}
