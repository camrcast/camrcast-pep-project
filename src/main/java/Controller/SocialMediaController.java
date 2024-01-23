package Controller;
import java.util.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.sql.*;
import DAO.*;
import Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import Util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        ObjectMapper o = new ObjectMapper();
        Connection c = ConnectionUtil.getConnection();
        Javalin app = Javalin.create();
        app.post("/register", ctx -> {
            Account b = SocialMediaDAO.register(o.readValue(ctx.body(), Account.class), c);
            if (b.getAccount_id() == -1){
                ctx.status(400);
            }
            else{
                ctx.json(b);
            }
        });
        app.post("/login", ctx -> {
            Account b = SocialMediaDAO.login(o.readValue(ctx.body(), Account.class), c);
            if (b.getAccount_id() == -1){
                ctx.status(401);
            }
            else{
                ctx.json(b);
            }
        });
        app.get("/messages", ctx -> {
            List<Message> b = SocialMediaDAO.getAllMessages(c);
            ctx.json(b);
        });
        app.get("/messages/{message_id}", ctx -> {
            String messid = ctx.pathParam("message_id");
            Message b = SocialMediaDAO.getMessage(Integer.parseInt(messid), c);
            if (b.getMessage_id() == -1){
                ctx.status(200);
            }
            else{
                ctx.json(b);
            }
        });
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}