package com.example.lambo.dataclasss;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/19.
 */
public class ZAXJGClass implements Serializable {
    private ArrayList<ALIST> accounting_list;
    private ArrayList<ALIST> accountingz_list;
    private ArrayList<AFOREMAN> accounting_foreman;
    private ArrayList<AFOREMAN> accountingz_foreman;

    @Override
    public String toString() {
        return "ZAXJGClass{" +
                "accounting_list=" + accounting_list +
                ", accountingz_list=" + accountingz_list +
                ", accounting_foreman=" + accounting_foreman +
                ", accountingz_foreman=" + accountingz_foreman +
                '}';
    }

    public ArrayList<ALIST> getAccounting_list() {
        return accounting_list;
    }

    public void setAccounting_list(ArrayList<ALIST> accounting_list) {
        this.accounting_list = accounting_list;
    }

    public ArrayList<ALIST> getAccountingz_list() {
        return accountingz_list;
    }

    public void setAccountingz_list(ArrayList<ALIST> accountingz_list) {
        this.accountingz_list = accountingz_list;
    }

    public ArrayList<AFOREMAN> getAccounting_foreman() {
        return accounting_foreman;
    }

    public void setAccounting_foreman(ArrayList<AFOREMAN> accounting_foreman) {
        this.accounting_foreman = accounting_foreman;
    }

    public ArrayList<AFOREMAN> getAccountingz_foreman() {
        return accountingz_foreman;
    }

    public void setAccountingz_foreman(ArrayList<AFOREMAN> accountingz_foreman) {
        this.accountingz_foreman = accountingz_foreman;
    }

    public class ALIST {
        String addr;
        String addtime;
        String day_hours;
        float equal_day;
        int foreman_id;
        String foreman_name;
        int id;
        String starttime;
        int user_id;
        String wage;
        String work_hours;
        int work_type;

        public String getForeman_name() {
            return foreman_name;
        }

        public void setForeman_name(String foreman_name) {
            this.foreman_name = foreman_name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDay_hours() {
            return day_hours;
        }

        public void setDay_hours(String day_hours) {
            this.day_hours = day_hours;
        }

        public float getEqual_day() {
            return equal_day;
        }

        public void setEqual_day(int equal_day) {
            this.equal_day = equal_day;
        }

        public int getForeman_id() {
            return foreman_id;
        }

        public void setForeman_id(int foreman_id) {
            this.foreman_id = foreman_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getWage() {
            return wage;
        }

        public void setWage(String wage) {
            this.wage = wage;
        }

        public String getWork_hours() {
            return work_hours;
        }

        public void setWork_hours(String work_hours) {
            this.work_hours = work_hours;
        }

        public int getWork_type() {
            return work_type;
        }

        public void setWork_type(int work_type) {
            this.work_type = work_type;
        }

        @Override
        public String toString() {
            return "ALIST{" +
                    "addr='" + addr + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", day_hours='" + day_hours + '\'' +
                    ", equal_day=" + equal_day +
                    ", foreman_id=" + foreman_id +
                    ", id=" + id +
                    ", starttime='" + starttime + '\'' +
                    ", user_id=" + user_id +
                    ", wage='" + wage + '\'' +
                    ", work_hours='" + work_hours + '\'' +
                    ", work_type=" + work_type +
                    '}';
        }
    }

    public class AFOREMAN {
        int foreman_id;
        String addtime;
        String day_hours;
        String day_price;
        String hours_price;
        String phonenum;
        String username;
        int work_type;
        int user_id;
        int stat;

        public int getForeman_id() {
            return foreman_id;
        }

        public void setForeman_id(int foreman_id) {
            this.foreman_id = foreman_id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDay_price() {
            return day_price;
        }

        public void setDay_price(String day_price) {
            this.day_price = day_price;
        }

        public String getDay_hours() {
            return day_hours;
        }

        public void setDay_hours(String day_hours) {
            this.day_hours = day_hours;
        }

        public String getHours_price() {
            return hours_price;
        }

        public void setHours_price(String hours_price) {
            this.hours_price = hours_price;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public int getWork_type() {
            return work_type;
        }

        public void setWork_type(int work_type) {
            this.work_type = work_type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }

        @Override
        public String toString() {
            return "AFOREMAN{" +
                    "foreman_id=" + foreman_id +
                    ", addtime='" + addtime + '\'' +
                    ", day_hours='" + day_hours + '\'' +
                    ", day_price='" + day_price + '\'' +
                    ", hours_price='" + hours_price + '\'' +
                    ", phonenum='" + phonenum + '\'' +
                    ", username='" + username + '\'' +
                    ", work_type=" + work_type +
                    ", user_id=" + user_id +
                    ", stat=" + stat +
                    '}';
        }
    }
}
