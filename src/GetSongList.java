

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import edu.pitt.spotify.utils.*;

/**
 * Servlet implementation class GetSongList
 */
@WebServlet("/GetSongList")
public class GetSongList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSongList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String title = "";
		
		try {
			DbUtilities db = new DbUtilities();
			String sql = "Select * from song order by title asc;";
			
			if(! title.equals("")) {
				sql += " where title like '%" + title + "%' ";
			}
			
			sql += "order by asc;";
			
			ResultSet rs = db.getResultSet(sql);
			JSONArray songList = new JSONArray();
			while(rs.next()) {
				JSONObject song = new JSONObject();
				song.put("id", rs.getString("title"));
				song.put("title", rs.getString("song_id"));
				
				songList.put(song);
			}
			
			response.getWriter().write(songList.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
