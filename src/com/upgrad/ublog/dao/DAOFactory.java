package com.upgrad.ublog.dao;

/**
 * TODO: 3.8. Provide a factory method which returns PostDAOImpl object. (Hint: Return type
 *  of this method should be PostDAO)
 * TODO: 3.9. Provide a factory method which returns UserDAOImpl object. (Hint: Return type
 *  of this method should be UserDAO)
 */

public class DAOFactory {
    private static UserDAOImpl obj;
    private static PostDAOImpl obj1;
public static UserDAO factory(){
    if(obj==null){
        obj=UserDAOImpl.getInstance();
    }
    return obj;
}
public static PostDAO factorypost(){
    if(obj1==null){
        obj1=PostDAOImpl.getInstance();
    }
    return obj1;
}
}
