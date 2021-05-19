package test.emailservice;

import test.user.User;

public class MailService {

    public static Boolean send(User receiver) throws Exception {
        try {
            return receiver.isUser18();
        } catch (Exception e) {
            throw  e;
        }
    }
}
