package com.gmail.sge.serejka.happyurl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class URLStatDTO extends URLResultDTO{

    private long redirects;
    private Date lastAccess;

    public long getRedirects() {
        return redirects;
    }

    public void setRedirects(long redirects) {
        this.redirects = redirects;
    }

    public String getLastAccess() {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(lastAccess);
    }


    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
}
