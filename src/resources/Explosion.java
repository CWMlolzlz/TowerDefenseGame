package resources;

import java.io.File;
import java.io.IOException;

import game.Play;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

public class Explosion{

	private static File file = new File("data/particles/explosion.xml");
	public ConfigurableEmitter ce;
	
	public float x,y;
	private int decay = 120;
	
	public Explosion(float newx, float newy) {
		x = newx;
		y = newy;
		try {
			ce = ParticleIO.loadEmitter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ce.setPosition(newx, newy, false);
		ce.setEnabled(true);
		Play.ps.addEmitter(ce);
		Play.explosions.add(this);		
	}
	
	public void updateDecay(){
		if(decay <= 10){
			ce.setEnabled(false);
			try {this.finalize();}catch(Throwable e){e.printStackTrace();}
			Play.ps.removeEmitter(ce);
			Play.explosions.remove(ce);
		}else{
			decay--;
		}
	}
	
}