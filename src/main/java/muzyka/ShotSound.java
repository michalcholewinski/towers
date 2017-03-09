package muzyka;
/*
 *	SimpleAudioPlayer.java
 *
 *	This file is part of jsresources.org
 */

/*
 * Copyright (c) 1999 - 2001 by Matthias Pfisterer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Klasa odpowiedzialna za odtwarzanie dzwiêków uderzenia
 * 
 *
 */
public class ShotSound extends Thread{
	private static final int	EXTERNAL_BUFFER_SIZE = 128000;
	private final String HIT_PLAYER_SOUND_NAME = "explode.wav";
	private final String HIT_ANOTHER_BULLET_SOUND_NAME = "explode2.wav";
	private final int HILL = 0; //s³u¿y do sprawdzania czy by³a kolizja ze wzgórzem,
	private final int PLAYER = 1; //czy z jedn¹ z wie¿
	
	
	String	strFilename;
	File	soundFile;
	private boolean shot;
	
	public ShotSound(){
		strFilename=HIT_PLAYER_SOUND_NAME;
		shot=false;
	}




	public void setShot(boolean shot){
		this.shot=shot;
	}
	
	
	public void setFilename(int which){
		if(which==HILL){
			strFilename=HIT_PLAYER_SOUND_NAME;
		}
		else if(which==PLAYER){
			strFilename=HIT_ANOTHER_BULLET_SOUND_NAME;
		}
	}
	
	public void music(){
		

		soundFile = new File(strFilename);
	
		AudioInputStream	audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			System.exit(1);
		}

		
		AudioFormat	audioFormat = audioInputStream.getFormat();

		SourceDataLine	line = null;
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class,
												 audioFormat);
		try
		{
			line = (SourceDataLine) AudioSystem.getLine(info);

			
			line.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

	
		line.start();


		int	nBytesRead = 0;
		byte[]	abData = new byte[EXTERNAL_BUFFER_SIZE];
		while (nBytesRead != -1)
		{
			try
			{
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nBytesRead >= 0)
			{
				int	nBytesWritten = line.write(abData, 0, nBytesRead);
			}
		}

		line.drain();

		line.close();
		
	//	System.exit(0);
	}
	
	public void run(){
		while(true){
			if(shot){
				music();
				shot=false;
			}
		}
	}

}