package com.upgrad.ublog.services;

/**
 * TODO: 3.14. Provide a factory method which returns PostServiceImpl object. (Hint: Return type
 *  of this method should be PostService)
 * TODO: 3.15. Provide a factory method which returns UserServiceImpl object. (Hint: Return type
 *  of this method should be UserService)
 */

public class ServiceFactory {
   // private PostService temp;
    private UserService temp1;
    private PostService objectofservice;
    public PostService factoryOfService()throws Exception{
    if(objectofservice==null) {
        objectofservice = PostServiceImpl.getobject();
    }
    return objectofservice;
}
public UserService getInstance(){
    if(temp1==null){
        temp1=UserServiceImpl.getInstance();
    }
    return temp1;
}
}
