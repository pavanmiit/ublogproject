package com.upgrad.ublog.services;

/**
 * TODO: 3.22. Implement the PostService interface and implement this class using the Singleton pattern.
 *  (Hint: Should have a private no-arg Constructor, a private static instance attribute of type
 *  PostServiceImpl and a public static getInstance() method which returns the instance attribute.)
 * TODO: 3.23. Provide an attribute of type PostDAO and instantiate it using the DAOFactory class.
 *  Note: You should not have any reference to PostDAOImpl in this class.
 * TODO: 3.24. Define the following methods and return null for each of them. You will provide a
 *  proper implementation for each of these methods, later in this project.
 *  a. getPostsByEmailId()
 *  b. getPostsByTag()
 *  c. getAllTags()
 *  d. deletePost() (return false for this method for now)
 * TODO: 3.25. create() method should take post details as input and insert these details into
 *  the database using the create() method of PostDAO interface. Return the Post object which
 *  was returned by the create() method of the PostDAO interface.
 *  Note: The exception passed by DAO layer should not be passed to the presentation layer. Print the stack
 *  trace corresponding to the exception passed by DAO layer and throw a new exception of type Exception
 *  with a message "Some unexpected error occurred!"
 */

/**
 * TODO: 4.2. Implement getPostsByEmailId() method which takes email id as an input parameter and
 *  returns all the posts corresponding to the email id using the findByEmailId() method of PostDAO interface.
 *  Note: The exception passed by DAO layer should not be passed to the presentation layer. Print the stack
 *  trace corresponding to the exception passed by DAO layer and throw a new exception of type Exception
 *  with a message "Some unexpected error occurred!"
 */

/**
 * TODO: 4.6. Implement deletePost() method which takes post id and email id as an input parameter.
 *  1. Get the post corresponding to the post id using the findByPostId() method of the PostDAO interface.
 *  2. If no post exists corresponding the post id, then throw a new PostNotFoundException with
 *   "No Post exist with the given Post Id" message.
 *  3. If the post was created by the same user whose email id is passed as an input argument, then delete
 *   the post using deleteByPostId() method of PostDAO and return the same boolean value returned by the
 *   deleteByPostId() method.
 *  4. If the post was not created by the same user whose email id is passed as an input argument, don't delete
 *   the post, and return false.
 *  Note: The exception passed by DAO layer should not be passed to the presentation layer. Print the stack
 *  trace corresponding to the exception passed by DAO layer and throw a new exception of type Exception
 *  with a message "Some unexpected error occurred!"
 */

/**
 * TODO: 4.10. Implement the getAllTags() method which retrieves the list of all the tags from the
 *  database using the findAllTags() method of PostDAO interface and return a set of unique tags.
 *  Note: The exception passed by DAO layer should not be passed to the presentation layer. Print the stack
 *  trace corresponding to the exception passed by DAO layer and throw a new exception of type Exception
 *  with a message "Some unexpected error occurred!"
 */

import com.upgrad.ublog.dao.DAOFactory;
import com.upgrad.ublog.dao.PostDAO;
import com.upgrad.ublog.dao.PostDAOImpl;
import com.upgrad.ublog.db.Database;
import com.upgrad.ublog.dtos.Post;
import com.upgrad.ublog.exceptions.PostNotFoundException;
//import com.upgrad.ublog.dtos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: 4.11. Implement getPostsByTag() method which takes tag as an input parameter and
 *  returns all the posts corresponding to the tag using the findByTag() method of PostDAO interface.
 *  Note: The exception passed by DAO layer should not be passed to the presentation layer. Print the stack
 *  trace corresponding to the exception passed by DAO layer and throw a new exception of type Exception
 *  with a message "Some unexpected error occurred!"
 */

public class PostServiceImpl implements PostService{
private PostServiceImpl(){

}
    private PreparedStatement stmt;
public PostDAO postdao= DAOFactory.factorypost();
private static PostServiceImpl objofser;
public static PostServiceImpl getobject(){
    if(objofser==null){
        objofser=new PostServiceImpl();
    }return objofser;
}
    public Post create(Post posts) throws Exception{
        Connection connections= Database.getConnection();

        Statement statements = connections.createStatement();
        // String sqls = "insert into  user valus(?,?,?)";
        stmt=connections.prepareStatement("insert into  post valus(?,?,?,?,?)");

        stmt.setString(1,posts.getEmailId());
        stmt.setString(2,posts.getTag());
        stmt.setString(3,posts.getTitle());
        stmt.setString(4,posts.getTitle());
        stmt.setString(5,posts.getDescription());
        stmt.executeUpdate();
        return posts;
    }
    public List<Post> getPostsByEmailId(String emailId) throws Exception{
        Connection connection=Database.getConnection();
        List<Post> postlist=new ArrayList<>();

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime;
        Statement statement = connection.createStatement();
        String sql = "select * from post where emailId = " + emailId;

            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet==null){
           throw new PostNotFoundException("No record found from thih email.id");}
        while(resultSet.next()) {
            Post post=new Post();
            post.setPostId(resultSet.getInt("tostId"));
            post.setEmailId(resultSet.getString("tmailId"));
            post.setTag(resultSet.getString("tag"));
            post.setTitle(resultSet.getString("title"));
            post.setDescription(resultSet.getString("description"));
            // post.setTimespamp(resultSet.("timestamp"));
            String time=resultSet.getString("timestamp");
            post.setTimespamp(LocalDateTime.parse(time));
            postlist.add(post);
        }
        return postlist;
}

    public List<Post> getPostsByTag(String tag) throws Exception{

        List<Post> taglist=postdao.findByTag(tag);
    return  taglist;
}

    public Set<String> getAllTags() throws Exception{
           List<String> tagss=postdao.findAllTags();
           Set<String> alltag=tagss.stream().collect(Collectors.toSet());
    return  alltag;
}

    public boolean deletePost(int postId, String emailId) throws Exception{
        Connection connection=Database.getConnection();
        LocalDateTime dateTime;
        Statement statement = connection.createStatement();
        String sql = "select * from post where postId = " + postId;
        ResultSet resultSet = statement.executeQuery(sql);
        Post post=new Post();
        if (resultSet.next()) {
            post.setPostId(resultSet.getInt("tostId"));
            post.setEmailId(resultSet.getString("tmailId"));
            post.setTag(resultSet.getString("tag"));
            post.setTitle(resultSet.getString("title"));
            post.setDescription(resultSet.getString("description"));
            // post.setTimespamp(resultSet.("timestamp"));
            String time=resultSet.getString("timestamp");
            post.setTimespamp(LocalDateTime.parse(time));
        }else if(post.getEmailId()==emailId){
            PostDAO delet= PostDAOImpl.getInstance();
            delet.deleteByPostId(postId);
            return true;

        }else throw new PostNotFoundException("post not found by this mail & id");


    return  false;}
}
