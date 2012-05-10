package ie.cit.cloudapp.web;

import ie.cit.cloudapp.JdbcTodoRepository;
import ie.cit.cloudapp.Todo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

@Controller
public class TodoRestController {
	
	@Autowired
	private JdbcTodoRepository repo;
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public @ResponseBody
	List<Todo> listTodos() {
		return repo.getAll();
	}
	
	@RequestMapping(value = "/todo/{todoId}", method = RequestMethod.GET)
	public @ResponseBody
	Todo todo(@PathVariable Integer todoId) {
		return repo.get(todoId);
	}
	
	@RequestMapping(value="/todo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createTodo(@RequestBody Todo todo, HttpServletRequest request,
			HttpServletResponse response) {
		repo.save(todo);
		response.addHeader("Location", 
				getLocationForChildResource(request, todo.getId()));
	}
	
	private String getLocationForChildResource(HttpServletRequest request,
			Integer childIdentifier) {
		//get the current URL from the request
		final StringBuffer url = request.getRequestURL();
		//append the /xyz to the URL and make it a UriTemplate
		final UriTemplate template = new UriTemplate(url.append("/{childId}")
				.toString());
		return template.expand(childIdentifier).toASCIIString();
	}
	
	@RequestMapping(value="/todo/{todoId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateTodo(@PathVariable Integer todoId, @RequestBody Todo todo) {
		Todo current = repo.get(todoId);
		current.setText(todo.getText());
		current.setDone(todo.isDone());
		repo.update(current);
	}
	
	@RequestMapping(value = "/todo/{todoId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTodo(@PathVariable Integer todoId) {
		repo.delete(todoId);
	}
}
