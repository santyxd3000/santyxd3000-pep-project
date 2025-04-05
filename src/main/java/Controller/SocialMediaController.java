package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        
        app.post("/register",this::registerAccountHandler);
        app.post("/login",this::postLoginHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByMessageIdHandler);
        app.post("/messages",this::postMessageHandler);
        app.patch("/messages/{message_id}",this::updateMessageHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler);
        app.get("/accounts/{account_id}/messages",this::getAllMessagesByAccountIdHandler);

        

// POST register
// POST login
// POST messages
// GET messages
// GET messages/{message_id}
// DELETE messages/{message_id}
//PATCH messages/{message_id}
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

        private void registerAccountHandler(Context ctx) throws JsonProcessingException {
       
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account addedAccount = accountService.registerAccount(account);
    
            Map<String, Object> response = new HashMap<>();
            response.put("account_id", addedAccount.getAccount_id());
            response.put("username", addedAccount.getUsername());
            response.put("password", addedAccount.getPassword()); // Should be removed from the response

            String jsonResponse = mapper.writeValueAsString(response);

            ctx.status(200).json(jsonResponse);
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            } catch (JsonProcessingException e) {
                ctx.status(400).result("Invalid JSON format in request body.");
            }
        }

        public void postLoginHandler(Context ctx) {
            Account credentials = ctx.bodyAsClass(Account.class);
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            try {
                Account account = accountService.login(username,password);
                Map<String,Object> response = new HashMap<>();
                response.put("account_id",account.getAccount_id());
                response.put("username",account.getUsername());
                response.put("password",account.getPassword());

                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(401).json(e.getMessage());
        }
    }

    private void postMessageHandler(Context ctx) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message addedMessage = messageService.postMessage(message);

            ctx.status(200).json(addedMessage);


        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (JsonProcessingException e) {
            ctx.status(400).result("Invalid JSON format in request body.");
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByMessageIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200).result("");
        }

    }

        private void getAllMessagesByAccountIdHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        
        List<Message> messages = messageService.getMessagesByAccountId(account_id);
    
        if (messages != null) {
            ctx.json(messages);
        } else {
            ctx.status(200).result("");
        }
    }

    private void updateMessageHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();

        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(message_id, message);

            ctx.status(200).json(updatedMessage);

        } catch (IllegalArgumentException e) {
           
            ctx.status(400).result(e.getMessage());
        } catch (JsonProcessingException e) {
            
            ctx.status(400).result("Invalid JSON format in request body.");
        }
        }


        private void deleteMessageHandler (Context ctx) {
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessage(message_id);
            if (deletedMessage != null) {
                ctx.status(200).json(deletedMessage);
            } else {
                ctx.status(200).result("");
            }
        }
}




