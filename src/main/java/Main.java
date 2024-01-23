import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Account;
import Util.ConnectionUtil;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        ConnectionUtil.resetTestDatabase();
        SocialMediaController socialMediaController = new SocialMediaController();
        Javalin app = socialMediaController.startAPI();
        HttpClient webClient = HttpClient.newHttpClient();
        app.start(8080);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response1 = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse response2 = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status1 = response1.statusCode();
        int status2 = response2.statusCode();
        Assert.assertEquals(200, status1);
        Assert.assertEquals(400, status2);
        Assert.assertEquals("", response2.body().toString());
    }
}
