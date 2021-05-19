package test.component;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

@Component
public class HelperComponent {

    public boolean checkEmail(String email) {
        System.out.println(email);
        throw new NotImplementedException();
    }
}
