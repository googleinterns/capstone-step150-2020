package com.google.sps.data;

import com.google.appengine.api.datastore.EmbeddedEntity;

<<<<<<< HEAD
=======
//Member object representing members of the Room

>>>>>>> 01e304e801ebc27598710258f75686e934426667
public class Member {
    private static final String ALIAS_PROPERTY = "alias";
    private String alias;

<<<<<<< HEAD
    private Member(String alias) {
        this.alias = alias;
    }
    public static Member createNewMember(String alias) {
        return new Member(alias);
    }
    public String getAlias(){
        return this.alias;
    }
=======
    //Member constructor
    private Member(String alias) {
        this.alias = alias;
    }

    //Creates a new member
    public static Member createNewMember(String alias) {
        return new Member(alias);
    }

    //Returns the alias of the member
    public String getAlias(){
        return this.alias;
    }

    //Turns a member object into an embedded entity
>>>>>>> 01e304e801ebc27598710258f75686e934426667
    public static EmbeddedEntity toEmbeddedEntity(Member member) {
        EmbeddedEntity memberEntity = new EmbeddedEntity();
        memberEntity.setProperty(ALIAS_PROPERTY, member.getAlias());
        return memberEntity;
    }
<<<<<<< HEAD
    public static Member fromEmbeddedEntity(EmbeddedEntity memberEntity) {
        return new Member((String) memberEntity.getProperties().get(ALIAS_PROPERTY));
    }
}
=======

    //Turns an embedded entity into a member object
    public static Member fromEmbeddedEntity(EmbeddedEntity memberEntity) {
        return new Member((String) memberEntity.getProperties().get(ALIAS_PROPERTY));
    }
}
>>>>>>> 01e304e801ebc27598710258f75686e934426667
