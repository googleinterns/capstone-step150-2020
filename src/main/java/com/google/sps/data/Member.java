package com.google.sps.data;

import com.google.appengine.api.datastore.EmbeddedEntity;

public class Member {
    private String alias;

    public Member(String alias) {
        this.alias = alias;
    }
    public String getAlias(){
        return this.alias;
    }
}