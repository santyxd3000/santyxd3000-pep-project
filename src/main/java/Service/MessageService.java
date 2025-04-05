package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message postMessage(Message message) throws IllegalArgumentException {
        int postedBy = message.getPosted_by();
        String text = message.getMessage_text();
        long time = message.getTime_posted_epoch();

        if (text == null || text.isBlank() || text.length()>255) {
            throw new IllegalArgumentException("");
        }

        if (!accountDAO.accountExistsById(postedBy)){
            throw new IllegalArgumentException("");
        }

        return messageDAO.insertMessage(new Message(postedBy, text, time));
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageByMessageId(messageId);
    }

    public List<Message> getMessagesByAccountId(int account_id){
        return messageDAO.getAllMessagesByAccountID(account_id);
    }

    public Message updateMessage(int message_id, Message message) {
        String text = message.getMessage_text();
        if (text == null || text.isBlank() || text.length() > 255) {
            throw new IllegalArgumentException("");
        }

        Message existingMessage = messageDAO.getMessageByMessageId(message_id);
        if (existingMessage == null) {
            throw new IllegalArgumentException("");
        }

        messageDAO.updatMessage(message_id,message);

        return messageDAO.getMessageByMessageId(message_id);
    }

    public Message deleteMessage (int message_id){
        return messageDAO.deleteMessageById(message_id);
    }




    
    
}

