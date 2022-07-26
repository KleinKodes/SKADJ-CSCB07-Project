package com.example.b07_project;

public class Event {
    protected int id;
    protected String name;

    public Event()
    {
    }
    public Event(String name)
    {
        this.name = name;
        this.id = 0;
    }
}
