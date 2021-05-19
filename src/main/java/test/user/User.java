package test.user;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import test.component.HelperComponent;

import test.todolistservice.Item;
import test.todolistservice.ToDoList;
import test.emailservice.MailService;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDate birthday;
    private ToDoList todolist;

    @Autowired
    private HelperComponent helperComponent;

    public ToDoList getToDoList(){
        return this.todolist;
    }

    public boolean isValid() {
        return this.helperComponent.checkEmail(this.email)
                && StringUtils.isNotBlank(this.firstname)
                && StringUtils.isNotBlank(this.lastname)
                && this.birthday != null
                && (StringUtils.length(this.password) >= 8 && StringUtils.length(this.password) <= 40)
                && LocalDate.now().minusYears(13).isAfter(this.birthday);
    }

    public void printToDoList(){
        for (Item it: this.todolist.getItems()) {
            System.out.println(it.getName() + " - " + it.getContent());
        }
    }

    public boolean createToDoList() throws Exception{
        if(this.todolist == null){
            this.todolist = ToDoList.builder().items(new ArrayList<Item>()).build();
            return true;
        } else {
            throw new Exception("This user already possesses a list, he can't create another one");
        }
    }

    public boolean addToDoList(ToDoList todolist) throws Exception{
        if(this.todolist == null){
            this.todolist = todolist;
            return true;
        } else {
            throw new Exception("This user already possesses a list, he can't add another one");
        }
    }

    public boolean isUser18() throws Exception {
        if(LocalDate.now().minusYears(18).isAfter(this.getBirthday())){
            return true;
        } else {
            throw new Exception("This user is under 18 years old.");
        }
    }
}
