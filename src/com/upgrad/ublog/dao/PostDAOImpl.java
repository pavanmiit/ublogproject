package com.upgrad.ublog.dao;

/**
 * TODO: 3.19. Implement the PostsDAO interface and implement this class using the Singleton pattern.
 *  (Hint: Should have a private no-arg Constructor, a private static instance attribute of type
 *  PostDAOImpl and a public static getInstance() method which returns the instance attribute.)
 * TODO: 3.20. Define the following methods and return null for each of them. You will provide a
 *  proper implementation for each of these methods, later in this project.
 *  a. findByEmailId()
 *  b. findByTag()
 *  c. findAllTags()
 *  d. findByPostId()
 *  e. deleteByPostId() (return false for this method for now)
 * TODO: 3.21. create() method should take post details as input and insert these details into
 *  the POST table. Return the same Post object which was passed as an input argument.
 *  (Hint: You should get the connection using the DatabaseConnection class)
 */

/**
 * TODO: 4.1. Implement findByEmailId() method which takes email id as an input parameter and
 *  returns all the posts corresponding to the email id from the Post table defined
 *  in the database.
 */

/**
 * TODO: 4.4. Implement the deleteByPostId() method which takes post id as an input argument and delete
 *  the corresponding post from the database. If the post was deleted successfully, then return true,
 *  otherwise, return false. (Hint: The executeUpdate() method returns the count of rows affected by the
 *  query.)
 * TODO: 4.5. Implement the findByPostId() method which takes post id as an input argument and return the
 *  post details from the database. If no post exists for the given id, then return a Post object
 *  without setting any of its attributes.
 */

import com.upgrad.ublog.db.Database;
import com.upgrad.ublog.dtos.Post;
import com.upgrad.ublog.dtos.User;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 4.8. Implement findAllTags() method which returns a list of all tags in the POST table.
 * TODO: 4.9. Implement findByTag() method which takes "tag" as an input argument and returns all the
 *  posts corresponding to the input "tag" from the POST table defined in the database.
 */

public class PostDAOImpl implements PostDAO{
    private PostDAOImpl() {
    }
    private PreparedStatement stmt;
    private static PostDAOImpl object;
    public static PostDAOImpl getInstance(){
        if(object==null){
            object=new PostDAOImpl();
        }
        return object;
    }
    @Override
    public Post create(Post post) throws SQLException{
        Connection connections=Database.getConnection();

        Statement statements = connections.createStatement();
        // String sqls = "insert into  user valus(?,?,?)";
        stmt=connections.prepareStatement("insert into  post valus(?,?,?,?,?)");

        stmt.setString(1,post.getEmailId());
        stmt.setString(2,post.getTag());
        stmt.setString(3,post.getTitle());
        stmt.setString(4,post.getDescription());
        stmt.setObject(5,post.getTimespamp());
        stmt.executeUpdate();
        return post;
    }
    @Override
    public List<Post> findByEmailId(String emailId) throws SQLException{
        Connection connection=Database.getConnection();
List<Post> postlist=new ArrayList<>();

       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime;
        Statement statement = connection.createStatement();
        String sql = "select * from post where emailId = " + emailId;
        ResultSet resultSet = statement.executeQuery(sql);

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
    @Override
    public List<Post> findByTag(String tag) throws SQLException{
        Connection connection=Database.getConnection();
        List<Post> postlist=new ArrayList<>();

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime;
        Statement statement = connection.createStatement();
        String sql = "select * from post where tag = " + tag;
        ResultSet resultSet = statement.executeQuery(sql);

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
    @Override
    public Post findByPostId(int postId) throws SQLException{
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
        }
        return post;
    }
    @Override
    public List<String> findAllTags() throws SQLException{
        Connection connection=Database.getConnection();
        List<String> postlist=new ArrayList<>();

        Statement statement = connection.createStatement();
        String sql = "select * from post";
        ResultSet resultSet = statement.executeQuery(sql);
           String tags;
        while(resultSet.next()) {
            tags=resultSet.getString("tag");
            postlist.add(tags);
        }
        return postlist;
    }
    @Override
    public boolean deleteByPostId(int postId) throws SQLException{
        Connection connection=Database.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete * from post where postId = " + postId;
       if( statement.executeUpdate(sql)==0){
           return false;
       }else
        return false;}
}
