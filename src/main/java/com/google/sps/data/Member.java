package com.google.sps.data;

import com.google.appengine.api.datastore.EmbeddedEntity;

public class Member {
    private static final String ALIAS_PROPERTY = "alias";
    private String alias;

    private Member(String alias) {
        this.alias = alias;
    }
    public static Member createNewMember(String alias) {
        return new Member(alias);
    }
    public String getAlias(){
        return this.alias;
    }
    public static EmbeddedEntity toEmbeddedEntity(Member member) {
        EmbeddedEntity memberEntity = new EmbeddedEntity();
        memberEntity.setProperty(ALIAS_PROPERTY, member.getAlias());
        return memberEntity;
    }
    public static Member fromEmbeddedEntity(EmbeddedEntity memberEntity) {
        return new Member((String) memberEntity.getProperties().get(ALIAS_PROPERTY));
    }
}