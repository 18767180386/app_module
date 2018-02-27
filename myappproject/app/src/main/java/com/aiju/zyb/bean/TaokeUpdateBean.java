package com.aiju.zyb.bean;

/**
 * Created by AIJU on 2017-06-05.
 */

public class TaokeUpdateBean {
    private int     versioncode;
    private String  updateinfo;
    private String  downloadurl;
    private int   versionupdate; // 1 普通更新   2强制更新

    public String getIsnewVersion() {
        return isnewVersion;
    }

    public void setIsnewVersion(String isnewVersion) {
        this.isnewVersion = isnewVersion;
    }

    private String  isnewVersion;  //0  没有   1有
    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public int getVersionupdate() {
        return versionupdate;
    }

    public void setVersionupdate(int versionupdate) {
        this.versionupdate = versionupdate;
    }


}
