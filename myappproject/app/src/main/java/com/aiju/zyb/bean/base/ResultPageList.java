package com.aiju.zyb.bean.base;

import java.util.List;

/**
 * Created by AIJU on 2017-04-14.
 */

public class ResultPageList<T> {
    public static final String RESULT_OK = "000";

    public static  final int Http_Success=200;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int status;



    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    private List<T> data;

    /*
    private DataList<T> data;

    public String getMsg() {
        return msg;
    }

    public DataList<T> getData() {
        return data;
    }

    public class DataList<T> {
        private String total;
        private ArrayList<T> result;
        private ArrayList<T> items;
        private String total_pv;
        private String total_uv;

        public ArrayList<T> getResult() {
            return result;
        }

        public String getTotal() {
            return total;
        }

        public ArrayList<T> getItems() {
            return items;
        }

        public String getTotal_pv() {
            return total_pv;
        }

        public String getTotal_uv() {
            return total_uv;
        }
    }
    */

}
