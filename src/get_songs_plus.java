/*
 * @author Riley O'Neill
 * @version 1.0
 */

/*
 * Makes database connection and returns relevant info
 * 
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pitt.spotify.utils.DbUtilities;

/**
 * Servlet implementation class get_songs_plus
 */
@WebServlet("/api/get_songs_plus")
public class get_songs_plus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public get_songs_plus() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		// Title
		// Artist
		// Album
		// Year
		String title = "", artist = "", album = "", songYear = "";
		String sql = "";
		if(request.getParameter("title") != null){
			title = request.getParameter("title");
			if(!title.equals("")){
				//query has been altered so that the search term may search the song title, album title, and artist name fields.
				sql += "SELECT s.song_id, s.title, art.band_name, art.first_name, art.last_name, alb.title as album_title, s.release_date, s.record_date, s.length FROM song as s"
						+ " join song_artist on fk_song_id = s.song_id "
						+ " join artist as art on art.artist_id = fk_artist_id"
						+ " join album_song on album_song.fk_song_id = s.song_id"
						+ " join album as alb on alb.album_id = album_song.fk_album_id"
						+ " WHERE s.title LIKE '" + title + "%'"
						+ " OR art.first_name LIKE '%" + title + "%'"
						+ " OR art.band_name LIKE '%" + title + "%' "
						+ " OR art.last_name LIKE '%" + title + "%' "
						+ " OR alb.title LIKE '%" + title + "%' ;";
			}
			//returns all songs if no info entered
			if(title.equals("")) {
				sql += "SELECT s.song_id, s.title, art.band_name, art.first_name, art.last_name, alb.title as album_title, s.release_date, s.record_date, s.length FROM song as s"
						+ " join song_artist on fk_song_id = s.song_id "
						+ " join artist as art on art.artist_id = fk_artist_id"
						+ " join album_song on album_song.fk_song_id = s.song_id"
						+ " join album as alb on alb.album_id = album_song.fk_album_id";
			}
			JSONArray songList = new JSONArray();

			try {
				DbUtilities db = new DbUtilities();
				ResultSet rs = db.getResultSet(sql);
				while(rs.next()){
					JSONObject song = new JSONObject();
					song.put("song_id", rs.getString("song_id"));
					song.put("title", rs.getString("title"));
					song.put("length", rs.getInt("length"));
					song.put("band_name", rs.getString("band_name"));
					song.put("album_title", rs.getString("album_title"));
					song.put("first_name", rs.getString("first_name"));
					song.put("last_name", rs.getString("last_name"));

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
		else if(request.getParameter("artist") != null){
			artist = request.getParameter("artist");
			if(! artist.equals("")){
				sql += "SELECT * from artist";
				sql += " where band_name like '%" + artist + "%'"
						+ " or first_name like '%" + artist + "%'"
						+ " or last_name like '%" + artist + "%'";
			}
			if(artist.equals("")) {
				sql += "SELECT * from artist";
			}
			JSONArray artistList = new JSONArray();

			try {
				DbUtilities db = new DbUtilities();
				ResultSet rs = db.getResultSet(sql);
				while(rs.next()){
					JSONObject artistObject = new JSONObject();
					artistObject.put("first_name", rs.getString("first_name"));
					artistObject.put("last_name", rs.getString("last_name"));
					artistObject.put("band_name", rs.getString("band_name"));
					artistObject.put("bio", rs.getString("bio"));

					artistList.put(artistObject);
				}
				response.getWriter().write(artistList.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if(request.getParameter("album")!=null){
			album = request.getParameter("album");
			if(!album.equals("")){
				sql += "SELECT album.title, album.release_date, recording_company_name, number_of_tracks, PMRC_rating, album.length, band_name, first_name, last_name from album " + 
						" join album_song as albs on albs.fk_album_id = album_id " + 
						" join song as s on albs.fk_song_id = song_id " + 
						" join song_artist as sa on sa.fk_song_id = song_id " + 
						" join artist on sa.fk_artist_id = artist_id " + 
						" WHERE s.title LIKE '" + album + "%'" + 
						" OR artist.first_name LIKE '%" + album + "%'" + 
						" OR artist.band_name LIKE '%" + album + "%'" + 
						" OR artist.last_name LIKE '%" + album + "%'" + 
						" OR album.title LIKE '%" + album + "%' ;";
			}
			if(album.equals("")) {
				sql += "SELECT album.title, album.release_date, recording_company_name, number_of_tracks, PMRC_rating, album.length, band_name, first_name, last_name from album " + 
						" join album_song as albs on albs.fk_album_id = album_id " + 
						" join song as s on albs.fk_song_id = song_id " + 
						" join song_artist as sa on sa.fk_song_id = song_id " + 
						" join artist on sa.fk_artist_id = artist_id ";
			}

			JSONArray albumList = new JSONArray();

			try {
				DbUtilities db = new DbUtilities();
				ResultSet rs = db.getResultSet(sql);
				while(rs.next()){
					JSONObject albumObject = new JSONObject();
					albumObject.put("title", rs.getString("title"));
					albumObject.put("release_date", rs.getString("release_date"));
					albumObject.put("recording_company_name", rs.getString("recording_company_name"));
					albumObject.put("number_of_tracks", rs.getInt("number_of_tracks"));
					albumObject.put("PMRC_rating", rs.getString("PMRC_rating"));
					albumObject.put("length", rs.getDouble("length"));
					albumObject.put("band_name", rs.getString("band_name"));
					albumObject.put("first_name", rs.getString("first_name"));
					albumObject.put("last_name", rs.getString("last_name"));

					albumList.put(albumObject);
				}
				response.getWriter().write(albumList.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
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
