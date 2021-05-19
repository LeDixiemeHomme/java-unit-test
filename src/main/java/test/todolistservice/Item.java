package test.todolistservice;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import test.component.HelperComponent;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private String name;
    private String content;
    private LocalDateTime creationDate;

    public static Item createItem(final String name, final String content) throws Exception {
        if(content.length() <= 1000){
            Item item = Item.builder()
                    .name(name)
                    .content(content)
                    .creationDate(LocalDateTime.now())
                    .build();
            return item;
        } else {
            throw new Exception("The content length is superior to the 1000 characters limit.");
        }
    }

    @Autowired
    private HelperComponent helperComponent;

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    public String getName(){
        return this.name;
    }

    public String getContent(){
        return this.content;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(this.name)
                && StringUtils.isNotBlank(this.content)
                && StringUtils.length(this.content) <= 100; //valeur passée de 1000 à 100 car problème d'espace mémoire
    }

}
