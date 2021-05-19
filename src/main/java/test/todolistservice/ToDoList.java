package test.todolistservice;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import test.component.HelperComponent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoList {

    private ArrayList<Item> items;

    @Autowired
    private HelperComponent helperComponent;

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public Item canAddItem(String name, String content) throws Exception {
        try {
            this.isValueInItems(name);
            this.isListSizeOver10();
            this.isLastItem30minOdl();
            Item item = Item.createItem(name, content);
            this.items.add(item);
            return item;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isValueInItems(String name) throws Exception{
        if(this.items != null){
            for(Item it : this.items){
                if(name == it.getName()){
                    throw new Exception("An item in this list already has the name \"" + it.getName() + "\".");
                }
            }
            return false;
        }
        return false;
    }

    public boolean isListSizeOver10() throws Exception{
        if(this.items.size() > 10){
            throw new Exception("The list can't contains more than 10 item.");
        } else {
            return false;
        }
    }

    public boolean isLastItem30minOdl() throws Exception {
        if(this.items.size() > 0) {
            Item lastItem = this.items.get(items.size()-1);
            if(lastItem.getCreationDate().plusMinutes(30).isAfter(LocalDateTime.now())) {
                throw new Exception("The 30 minutes delay between item creation isn't done yet.");
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
