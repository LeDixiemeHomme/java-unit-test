package test.todolistservice;

import test.component.HelperComponent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import test.todolistservice.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ItemTest {

    private Item item;

    private HelperComponent helperComponent;

    @Before
    public void beforeTest() throws Exception {
        this.item = Item.builder()
                .name("name")
                .content("content")
                .creationDate(LocalDateTime.now())
                .build();

        this.helperComponent = Mockito.mock(HelperComponent.class);

        this.item.setHelperComponent(this.helperComponent);
    }

    @Test
    public void testIsValidNominal() throws Exception {
        assertTrue(this.item.isValid());
    }

    @Test
    public void testIsValidName() {
        this.item.setName("name");
        assertTrue(this.item.isValid());
    }

    @Test
    public void testIsValidContentLength() {
        String long_content = "aaaaA1Aaaaa";
        for(int i = 0; i < 10; i++){
            long_content += long_content;
        }
        this.item.setContent(long_content);
        assertFalse(this.item.isValid());
    }
}
