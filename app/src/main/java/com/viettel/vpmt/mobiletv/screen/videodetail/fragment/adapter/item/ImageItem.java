package com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.item;

/**
 * Created by ThanhTD on 3/23/2016.
 */
public class ImageItem
{
    String uri;
    String des;

    public ImageItem(String uri, String des)
    {
        this.uri = uri;
        this.des = des;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getDes()
    {
        return des;
    }

    public void setDes(String des)
    {
        this.des = des;
    }
}
