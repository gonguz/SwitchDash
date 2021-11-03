package com.ucm.gdv.logic;

import com.ucm.gdv.engineinterface.IGraphics;

import java.util.LinkedList;
import java.util.List;

public class ParticleSystem {

    private IGraphics _graphics;

    private List<Particle> _particles;
    private final int NUM_PARTICLES = 35;

    public ParticleSystem(IGraphics graphics){

        _graphics = graphics;

        _particles = new LinkedList<>();
    }

    public void initParticleSystem(){
        for(int i = 0; i < NUM_PARTICLES; i++){
            //Para no crear particulas nuevas cada vez que llamo a este metodo
            _particles.add(new Particle(_graphics, 0, 0, 0, 0));
            _particles.get(i).setCanRender(false);
        }
    }

    public void setParticleProperties(int minX, int maxX, int minY, int maxY, Ball.COLORS color){
        for(int i = 0; i < NUM_PARTICLES; i++){
            //Para no crear particulas nuevas cada vez que llamo a este metodo
            if(!_particles.get(i).getCanRender()) {
                _particles.get(i).setParticleProperties(minX, maxX, minY, maxY, color);
            }
        }
    }

    public void renderParticleSystem(){
        for(int i = 0; i < NUM_PARTICLES; i++){
            if(_particles.size() > 0) {
                if(_particles.get(i).getCanRender()) {
                    _particles.get(i).drawParticle();
                }
            }
        }
    }

    public void updateParticleSystem(double elapsedTime, float scale){
        for(int i = 0; i < NUM_PARTICLES; i++){
            if(_particles.size() > 0) {
                _particles.get(i).updateParticle(elapsedTime, scale);
            }
        }
    }

}
