package com.example.army_app;

import java.math.BigInteger;
import java.util.ArrayList;

public class Session {
    BigInteger id;
    int session_number;
    public Session( BigInteger id, int session_number){
        this.id = id;
        this.session_number = session_number;
    }

    public int getSession_number() {
        return session_number;
    }

    public BigInteger getId() {
        return id;
    }
}
