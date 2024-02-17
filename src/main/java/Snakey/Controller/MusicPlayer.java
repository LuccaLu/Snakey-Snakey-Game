package Snakey.Controller;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * This is the class called MusicPlayer,
 * where the background music can be played.
 * @link N/A
 * @author Qiao Lu
 * */
public class MusicPlayer extends Thread
{
	private String m_FileName;
	private Player m_Player;

	public String GetFileName() {return m_FileName;}

	public void SetFileName(String m_FileName) {
		this.m_FileName = m_FileName;
	}

	public void SetPlayer(Player m_Player) {
		this.m_Player = m_Player;
	}

	public Player GetPlayer() {return m_Player;}

	/**
	 * Constructor A new MusicPlayer instance with the specified music file.
	 * @param m_FileName The music file that needs to be played
	 */
	public MusicPlayer(String m_FileName)
	{
		this.m_FileName = m_FileName;
	}

	/**
	 * This method is to play the music repeatedly in a separate thread.
	 * @param m_Player The new player that can play the certain music file
	 * @return null
	 * @see None
	 * @since Snakey Snakey 2.0 from Qiao Lu
	 * @version M1-updated
	 * @throws None if there is an error playing the background music(FileNotFoundException/IOException)
	 * @deprecated N/A
	 */
	public void Play()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					while(true){
						m_Player = new Player
								(new BufferedInputStream(new FileInputStream(m_FileName)));
						m_Player.play();
					}
				} catch (Exception e)
				{
					System.out.println(e);
				}
			}
		}.start();
	}

	/**
	 * This method is to create a new instance of MusicPlayer and initiate music playback.
	 * @param filename The music file that needs to be played
	 * @return null
	 * @see None
	 * @since Snakey Snakey 2.0 from Qiao Lu
	 * @version M1-updated
	 * @throws None
	 * @deprecated N/A
	 */
	public static void GetMusicPlay(String filename)
	{
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		musicPlayer.Play();
	}
}
