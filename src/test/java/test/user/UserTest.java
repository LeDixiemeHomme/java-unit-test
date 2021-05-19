package test.user;

import test.component.HelperComponent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import test.todolistservice.Item;
import test.todolistservice.ToDoList;
import test.user.User;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

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
    public void testIsValidNominal() {
        assertTrue(this.user.isValid());
    }

    @Test
    public void testIsNotValidEmailFormat() {
        this.user.setEmail("toto.fr");
        when(this.helperComponent.checkEmail(Mockito.anyString())).thenReturn(false);
        assertFalse(this.user.isValid());
    }

    @Test
    public void testIsNotValidFirstnameInvalid() {
        this.user.setFirstname(null);
        assertFalse(this.user.isValid());
    }

    @Test
    public void testIsNotValidLastnameInvalid() {
        this.user.setLastname(null);
        assertFalse(this.user.isValid());
    }

    @Test
    public void testIsNotValidBirthdayInvalid() {
        this.user.setBirthday(LocalDate.now().minusYears(2));
        assertFalse(this.user.isValid());
    }

    @Test
    public void testIsNotValidPasswordInvalid() {
        this.user.setPassword(null);
        assertFalse(this.user.isValid());
    }

    @Test (expected = Exception.class)
    public void testListCreationOverLimit() throws Exception {
        this.user.createToDoList();
        this.user.createToDoList();
    }

    @Test
    public void testListCreationUnderLimit() throws Exception {
        assertTrue(this.user.createToDoList());
    }

    @Test (expected = Exception.class)
    public void testListPossessionOverLimit() throws Exception {
        ToDoList toDoList1 = ToDoList.builder().items(new ArrayList<Item>()).build();
        ToDoList toDoList2 = ToDoList.builder().items(new ArrayList<Item>()).build();
        this.user.addToDoList(toDoList1);
        this.user.addToDoList(toDoList2);
    }

    @Test
    public void testListPossessionUnderLimit() throws Exception {
        ToDoList toDoList1 = ToDoList.builder().items(new ArrayList<Item>()).build();
        assertTrue(this.user.addToDoList(toDoList1));
    }
}
