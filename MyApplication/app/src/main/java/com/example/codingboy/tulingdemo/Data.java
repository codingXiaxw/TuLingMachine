package com.example.codingboy.tulingdemo;

/**
 * Created by codingBoy on 16/6/29.
 */
public class Data
{
    private String content;
    private int type;
    public int TYPE_RECEIVED=0;
    public int TYPE_SEND=1;

    public Data(String content,int type)
    {
        this.content=content;
        this.type=type;

    }

    public int getType()
    {
        return type;
    }
    public String getContent()
    {
        return content;
    }
}
