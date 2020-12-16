package com.upgrad.ublog;

import com.upgrad.ublog.dtos.Post;
import com.upgrad.ublog.dtos.User;
import com.upgrad.ublog.exceptions.PostNotFoundException;
import com.upgrad.ublog.services.*;
import com.upgrad.ublog.utils.DateTimeFormatter;
import com.upgrad.ublog.utils.LogWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Application {
    private Scanner scanner;
  //  private User obj;

    private PostService postService;
    private UserService userService;

    private boolean isLoggedIn;
    private String loggedInEmailId;

    public Application(PostService postService, UserService userService) {
        scanner = new Scanner(System.in);
        this.postService = postService;
        this.userService = userService;
        isLoggedIn = false;
        loggedInEmailId = null;
    }

    private void start() {
        boolean flag = true;

        System.out.println("*********************");
        System.out.println("********U-Blog*******");
        System.out.println("*********************");

        do {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Create Post");
            System.out.println("4. Search Post");
            System.out.println("5. Delete Post");
            System.out.println("6. Filter Post");
            System.out.println("7. Logout");
            System.out.println("8. Exit");

            System.out.print("\nPlease select an option: ");
            String choice = scanner.nextLine();
               try{
            switch (choice) {
                case "1": login(); break;
                case "2": register(); break;
                case "3": createPost(); break;
                case "4": searchPost(); break;
                case "5": deletePost(); break;
                case "6": filterPost(); break;
                case "7": logout(); break;
                case "8": flag=false; break;
                default:  System.out.println("Error"); break;
            }}catch (Exception e){System.out.println(e);}
        } while (flag);
    }

    /**
     * TODO 3.17. Implement the login() method. This method should prompt the user for the
     *  email id and the password. Use the login() method of the UserService interface
     *  to login into the application. If the user is successfully logged into the application,
     *  print "You are logged in." on the console, set isLoggedIn to true and set
     *  loggedInEmailId to the email id provided by the user.
     *  Catch all the exceptions thrown by the login() method of the UserService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */
    private void login() {
        if (isLoggedIn) {
            System.out.println("You are already logged in.");
            return;
        }

        System.out.println("*********************");
        System.out.println("********Login********");
        System.out.println("*********************");
        System.out.println("Enter userID");
        int id=scanner.nextInt();
        System.out.println("Enter mailID");
        String email=scanner.nextLine();
        System.out.println("Enter password");
        String password=scanner.nextLine();
        User obj=new User(id,email,password);

        try {
            UserService temp=UserServiceImpl.getInstance();
            if (temp.login(obj)) {
                System.out.println("You are logged in.");
                isLoggedIn = true;
                loggedInEmailId = obj.getEmailId();
            }
        } catch (Exception e) {
            //code to execute when account object was null
            System.out.println(e.getMessage());
        }

    }

    /**
     * TODO 3.18. Implement the register() method. This method should prompt the user for the
     *  email id and the password. Use the register() method of the UserService interface
     *  to register into the application. If the user is successfully registered into the application,
     *  print "You are logged in." on the console, set isLoggedIn to true and set
     *  loggedInEmailId to the email id provided by the user.
     *  Catch all the exceptions thrown by the register() method of the UserService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */
    private void register() {
        if (isLoggedIn) {
            System.out.println("You are already logged in.");
            return;
        }

        System.out.println("*********************");
        System.out.println("******Register*******");
        System.out.println("*********************");

        System.out.println("Enter userID");
        int id=scanner.nextInt();
        System.out.println("Enter mailID");
        String email=scanner.nextLine();
        System.out.println("Enter password");
        String password=scanner.nextLine();
        User obj=new User(id,email,password);

        try {
            UserService temp=UserServiceImpl.getInstance();
            if (temp.register(obj)) {
                System.out.println("You are logged in.");
                isLoggedIn = true;
                loggedInEmailId = obj.getEmailId();
            }
        } catch (Exception e) {
            //code to execute when account object was null
            System.out.println(e.getMessage());
        }

    }

    /**
     * TODO 3.26. Implement the createPost() method. This method should prompt the user for the
     *  post tag, the post title and the post description. Create a Post object with the following
     *  values:
     *   1. postId: 1
     *   2. emailId: loggedInEmailId
     *   3. tag, title, description: provided by the user
     *   4. timestamp: For now get the timestamp using the LocalDateTime.now() method
     *  Use the create() method of the PostService interface to create a new post into the database.
     *  Catch all the exceptions thrown by the create() method of the PostService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */

    /**
     * TODO 5.2: After saving the post details into the database using the createPost() method,\
     *  you should write logs in the following format.
     *  <formatted timestamp for that post><\t>New post with title [ <title for that post> ] created by [ <emailId of the creator> ]
     *  (Hint: you should use the LogWriter object)
     *  (Hint: Use the following method to get the user's current directory. All the logs should be stored
     *  in the file that is located at the following directory.
     *  System.getProperty("user.dir")
     *  Print the "System.getProperty("user.dir")" to know where the log file is created.
     */

    /**
     * TODO 6.1: Modify the existing code such that the following two operations occur simultaneously on
     *  two independent threads.
     *  thread1: Saving data into the database
     *  thread2: Writing logs into the file
     */
    private void createPost() {
        if (!isLoggedIn) {
            System.out.println("You are not logged in.");
            return;
        }

        System.out.println("*********************");
        System.out.println("*****Create Post*****");
        System.out.println("*********************");
        System.out.println("post tag");
        String tag=scanner.nextLine();
        System.out.println("post title");
        String title=scanner.nextLine();
        System.out.println("post discreption");
        String discreption=scanner.nextLine();
           try{
               String address="C:\\Users\\HP\\Desktop\\fsd-java-ublog\\src\\com\\upgrad\\ublog\\logsfile";
               String massage="New post with title ";
               Post newobject=new Post();
               newobject.setTitle(title);
               newobject.setTag(tag);
               newobject.setDescription(discreption);
               newobject.setEmailId(loggedInEmailId);
               newobject.setTimespamp(LocalDateTime.now());
               PostService setpost=PostServiceImpl.getobject();
               setpost.create(newobject);
               LogWriter.writeLog(massage,address);
           }catch(Exception e){System.out.println(e);}
    }

    /**
     * TODO 4.3. Implement the searchPost() method. This method should prompt the user for the
     *  email id. Use the getPostsByEmailId() method of the PostService interface to get all the
     *  posts corresponding to the provided email id. If there are no posts corresponding to the
     *  provided email id, then throw the PostNotFoundException with a message "Sorry no posts
     *  exists for this email id". Otherwise, print all the posts on the console.
     *  Catch all the exceptions thrown by the getPostsByEmailId() method of the PostService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */
    private void searchPost() throws Exception{
        if (!isLoggedIn) {
            System.out.println("You are not logged in.");
            return;
        }
        List<Post> output=new ArrayList<>();
        System.out.println("*********************");
        System.out.println("*****Search Post*****");
        System.out.println("*********************");
        System.out.println("enter email.ID");
        String mail=scanner.nextLine();
        PostService object=PostServiceImpl.getobject();
        output=object.getPostsByEmailId(mail);

    }

    /**
     * TODO 4.7. Implement the deletePost() method. This method should prompt the user for the
     *  post id. Use the deletePost() method of the PostService interface to delete the post
     *  corresponding to the post id. If the post was deleted successfully (deletePost() method of
     *  the PostService returns true), print the message "Post deleted successfully!" on the console.
     *  If the post was not deleted successfully (deletePost() method of the PostService returns false),
     *  print the message "You are not authorised to delete this post" on the console.
     *  Catch all the exceptions thrown by the deletePost() method of the PostService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */
    private void deletePost() {
        if (!isLoggedIn) {
            System.out.println("You are not logged in.");
            return;
        }

        System.out.println("*********************");
        System.out.println("*****Delete Post*****");
        System.out.println("*********************");
        try {
            System.out.println("enter postId which one you want to delet");
            int deletId = scanner.nextInt();
            postService = PostServiceImpl.getobject();
            if(postService.deletePost(deletId, loggedInEmailId)){
                System.out.println("Post deleted successfully!");
            }else System.out.println("you are not authorise to delet this post");
        }catch (Exception e){
          System.out.println(e);
        }

    }

    /**
     * TODO 4.12. Implement the filterPost() method. This method should show all the unique tags present
     *  in the POST table using the getAllTags() method of the PostService interface. Then it should
     *  prompt the user for the tag. Use the getPostsByTag() method of the PostService interface to get all the
     *  posts corresponding to the provided tag. If there are no posts corresponding to the
     *  provided tag, then throw the PostNotFoundException with a message "Sorry no posts
     *  exists for this tag". Otherwise, print all the posts on the console.
     *  Catch all the exceptions thrown by the getPostsByTag() method of the PostService interface with
     *  a single catch block which handles all exceptions using the Exception class and print the
     *  exception message using the getMessage() method.
     */
    private void filterPost()  {
        if (!isLoggedIn) {
            System.out.println("You are not logged in.");
            return;
        }
           try {
               System.out.println("*********************");
               System.out.println("*****Filter Post*****");
               System.out.println("*********************");
               Set<String> uniquetag = new HashSet<>(postService.getAllTags());
               System.out.println("enter tag name");
                String tag=scanner.nextLine();
                List<Post> uniquepost=new ArrayList<>(postService.getPostsByTag(tag));
                for(Post output: uniquepost){
                    System.out.println(output.toString());
                }
           }catch (Exception e){System.out.println(e.getMessage());}
    }

    private void logout() {
        if (!isLoggedIn) {
            System.out.println("You are not logged in.");
            return;
        }
        System.out.println("Logged out successfully");
        isLoggedIn = false;
        loggedInEmailId = null;
    }

    /**
     * TODO 3.16. Instantiate the userService and the postService variables using the ServiceFactory.
     */
    public static void main(String[] args) {
        ServiceFactory serviceFactory = new ServiceFactory();
        UserService userService = null;
        PostService postService = null;
        Application application = new Application(postService, userService);
        application.start();
    }
}
