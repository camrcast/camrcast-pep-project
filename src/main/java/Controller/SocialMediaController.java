package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.sql.*;
import DAO.*;
import Model.Account;
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
            try{
                Account b = AccountDAO.register(o.readValue(ctx.body(), Account.class), c);
                ctx.json(b);
            }
            catch (Exception e){
                ctx.status(400);
            }
        });
        app.post("/login", ctx -> {
            try{
                Account b = AccountDAO.login(o.readValue(ctx.body(), Account.class), c);
                ctx.json(b);
            }
            catch (Exception e){
                ctx.status(401);
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