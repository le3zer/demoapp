package ie.cit.cloudapp.web;

import ie.cit.cloudapp.Todo;
import ie.cit.cloudapp.JdbcTodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("todos")
@Controller
public class TodoController {
	
	@Autowired
	private JdbcTodoRepository repo;
	
	@RequestMapping(method = RequestMethod.GET)
	public void listTodos(Model model) {
		model.addAttribute("todos", repo.getAll());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void createTodo(Model model, @RequestParam String text) {
		Todo todo = new Todo();
		todo.setText(text);
		repo.save(todo);
		model.addAttribute("todos", repo.getAll());
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateTodo(Model model, @RequestParam int todoId) {
		Todo todo = repo.get(todoId);
		todo.setDone(!todo.isDone());
		repo.update(todo);
		model.addAttribute("todos", repo.getAll());
		}
	
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteTodo(Model model, @RequestParam int todoId) {
		repo.delete(todoId);
		model.addAttribute("todos", repo.getAll());
	}
	
}
