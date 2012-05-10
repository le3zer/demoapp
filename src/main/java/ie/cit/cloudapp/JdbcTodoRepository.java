package ie.cit.cloudapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

@Secured("ROLE_USER")
public class JdbcTodoRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTodoRepository(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	JdbcTodoRepository() {
		
	}
			
	public void save(Todo todo) {
		jdbcTemplate.update(
				"insert into TODO(text, done, owner) values(?,?,?)",
				todo.getText(), todo.isDone(), getCurrentUser());
	}
	
	public Todo get(int id){
		return jdbcTemplate.queryForObject(
				"select id, text, done from TODO where id=? and owner=?",
				new TodoMapper(), id, getCurrentUser());
	}
	
	public List<Todo> getAll(){
		return jdbcTemplate.query(
				"select id, text, done from TODO where owner=?", 
				new TodoMapper(), getCurrentUser());
	}
	
	private String getCurrentUser() {
	 return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	public void delete(int id) {
		jdbcTemplate.update("delete from TODO where id=? and owner=?", id,
				getCurrentUser());
	}
	
	public void update(Todo todo) {
		jdbcTemplate.update(
				"update TODO set text=?, done=? where id=? and owner=?",
				todo.getText(), todo.isDone(), todo.getId(), getCurrentUser());
		int id = jdbcTemplate.queryForInt( "select max(id) from TODO" );
		todo.setId(id);
	}

}

class TodoMapper implements RowMapper<Todo> {
	
	public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Todo todo = new Todo();
		todo.setId(rs.getInt("id"));
		todo.setText(rs.getString("text"));
		todo.setDone(rs.getBoolean("done"));
		return todo;
	}
}
