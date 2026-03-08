package Software.Side.Projects.REST_API;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")

public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long idCounter = 1L;

    @GetMapping
    public List<Task> getAllTasks(){
        return tasks;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        task.setId(idCounter++);
        tasks.add(task);
        return task;
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        tasks.removeIf(task -> task.getId().equals(id));
        return "Task Deleted!";
    }
}
