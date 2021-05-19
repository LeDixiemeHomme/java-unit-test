package test.emailservice;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import test.component.HelperComponent;
import test.user.User;

import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class MailServiceTest {

    private User user;

    private HelperComponent helperComponent;

    @Before
    public void beforeTest() {
        this.user = User.builder()
                .firstname("fname")
                .lastname("lname")
                .email("test@test.fr")
                .password("password")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        this.helperComponent = Mockito.mock(HelperComponent.class);
        when(this.helperComponent.checkEmail(Mockito.anyString())).thenReturn(true);

        this.user.setHelperComponent(this.helperComponent);
    }

    @Test
    public void testIsValidNominal() throws Exception {
        assertTrue(MailService.send(this.user));
    }

//    @Test(expected = Exception.class)
//    public void testIsNotValidNominal() throws Exception {
//        this.user.setBirthday(LocalDate.now().minusYears(17));
//        MailService.send(this.user);
//    }

}
