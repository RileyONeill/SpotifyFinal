

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class get_artists
 */
@WebServlet("/api/get_artists")
public class get_artists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_artists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		
		String artist = "", song = "", year = "";
		String sql = "";
		if(request.getParameter("artist") != null){
			artist = request.getParameter("artist");
			if(!artist.equals("")){
				sql += "SELECT s.song_id, s.title, art.band_name, alb.title as album_title, s.release_date, s.record_date, s.length FROM song as s"
						+ " join song_artist on fk_song_id = s.song_id "
						+ " join artist as art on art.artist_id = fk_artist_id"
						+ " join album_song on album_song.fk_song_id = s.song_id"
						+ " join album as alb on alb.album_id = album_song.fk_album_id";
				sql += " WHERE art.band_name LIKE '%" + artist + "%' ";
				sql += " OR art.first_name LIKE '%" + artist + "%' ";
				sql += " OR art.last_name LIKE '%" + artist + "%'; ";
			}			
		}
		
		
		if(sql.equals("")){
			sql = "SELECT * FROM artist;";
		}
		
		 response.getWriter().write(sql);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
