package main;

import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.mesh.LaplacianSmooth;

import java.util.EventListener;

/**
 * Author: mrzl
 * Date: 06.02.14
 * Time: 21:27
 * Project: MeshBrushDrawer
 */
public class VolumeDrawer extends PApplet implements EventListener {

    private MeshRenderer meshRenderer;
    private PeasyCam cam;

    //RandomCircleRing rcr;
    ThreeLinesRing tlr;


    /**
     * Just initializing some global objects.
     */
    public void setup() {
        //size( 1920, 1080, P3D );
        size( 1280, 720, P3D );

        this.meshRenderer = new MeshRenderer( this );

        this.cam = new PeasyCam( this, 1000 );

        //rcr = new RandomCircleRing( this );
        //rcr.generateMesh( 100, 0.2f );

        tlr = new ThreeLinesRing( this );
        tlr.generateMesh( 3, 100, 100 );

    }

    /**
     * Drawing the current mesh.
     */
    public void draw() {
        if ( this.frame != null )
            this.frame.setTitle( "FPS: " + ( int ) ( this.frameRate ) + " Vertices: " + tlr.getModifiedMesh().getNumVertices() );

        background( 0 );
        lights();

        meshRenderer.render( this.tlr.getModifiedMesh() );

    }

    public void keyPressed() {
        switch ( key ) {
            case 's':
                new LaplacianSmooth().filter( tlr.getModifiedMesh(), 1 );
                break;
        }
    }

}
