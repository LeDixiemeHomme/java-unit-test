package test.todolistservice;

import org.apache.commons.lang3.StringUtils;
import test.component.HelperComponent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import test.todolistservice.ToDoList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ToDoListTest {

    private ToDoList todolist;
    private Item item;

    private HelperComponent helperComponent;
    @Before
    public void beforeTest() {
        this.todolist = ToDoList.builder()
                .items(new ArrayList<Item>())
                .build();

        this.item = Item.builder()
                .name("name")
                .content("content")
                .creationDate(LocalDateTime.now())
                .build();

        this.helperComponent = Mockito.mock(HelperComponent.class);
        when(this.helperComponent.checkEmail(Mockito.anyString())).thenReturn(true);

        this.todolist.setHelperComponent(this.helperComponent);
    }

    @Test
    public void testIsValidNominal() throws Exception {
        assertNotNull(this.todolist.canAddItem(this.item.getName(), this.item.getContent()));
    }

    @Test
    public void testIsValueNotIn() throws Exception {
        String name1 = "name1";
        this.todolist.canAddItem("name2", "content");
        assertFalse(this.todolist.isValueInItems(name1));
    }

    @Test (expected = Exception.class)
    public void testIsValueIn() throws Exception {
        String name1 = "name1";
        this.todolist.canAddItem("name1", "content");
        this.todolist.isValueInItems(name1);
    }

    @Test
    public void testIsSizeNotOver10() throws Exception {
        for(int i = 0; i < 10; i++){
            Item item = Item.builder()
                    .name(Integer.toString(i))
                    .content("a content")
                    .creationDate(LocalDateTime.now().plusMinutes(31*i))
                    .build();
            this.todolist.getItems().add(item);
        }
        assertFalse(this.todolist.isListSizeOver10());
    }

    @Test (expected = Exception.class)
    public void testIsSizeOver11() throws Exception {
        for(int i = 0; i < 11; i++){
            Item item = Item.builder()
                    .name(Integer.toString(i))
                    .content("a content")
                    .creationDate(LocalDateTime.now().plusMinutes(31*i))
                    .build();
            this.todolist.getItems().add(item);
        }
        this.todolist.isListSizeOver10();
    }

    @Test
    public void testIsLastItem30minOdl() throws Exception {
        Item item = Item.builder()
                .name("name")
                .content("a content")
                .creationDate(LocalDateTime.now().minusMinutes(31))
                .build();
        this.todolist.getItems().add(item);
        assertTrue(this.todolist.isLastItem30minOdl());
    }

    @Test (expected = Exception.class)
    public void testIsLastItemNot30minOdl() throws Exception {
        Item item = Item.builder()
                .name("name")
                .content("a content")
                .creationDate(LocalDateTime.now().minusMinutes(29))
                .build();
        this.todolist.getItems().add(item);
        this.todolist.isLastItem30minOdl();
    }
}
