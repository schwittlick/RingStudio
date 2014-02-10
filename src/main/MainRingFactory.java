package main;

import main.RingCreationChooserDialog;
import main.io.MeshRenderer;
import main.misc.MeshUtils;
import main.rings.HemRingCreator;
import main.rings.HerringStingerRingCreator;
import main.rings.RingCreationStrategy;
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
public class MainRingFactory extends PApplet implements EventListener {

    private MeshRenderer meshRenderer;
    private PeasyCam cam;

    RingCreationStrategy rcs;

    public void init() {
        RingCreationChooserDialog dialog = new RingCreationChooserDialog( this );
        dialog.pack();
        dialog.setVisible( true );
    }

    /**
     * Just initializing some global objects.
     */
    public void setup() {
        //size( 1920, 1080, P3D );
        size( 1280, 720, P3D );

        this.meshRenderer = new MeshRenderer( this );

        this.cam = new PeasyCam( this, 1000 );
    }

    /**
     * Drawing the current mesh.
     */
    public void draw() {
        if ( this.frame != null )
            this.frame.setTitle( "FPS: " + ( int ) ( this.frameRate ) + " Vertices: " + rcs.getModifiedMesh().getNumVertices() );

        background( 0 );
        lights();

        meshRenderer.render( this.rcs.getModifiedMesh() );

    }

    public void keyPressed() {
        switch ( key ) {
            case 's':
                new LaplacianSmooth().filter( rcs.getModifiedMesh(), 1 );
                break;
            case 'e':
                rcs.exportMesh( "three_ring" + MeshUtils.getTimeStamp() + ".stl" );
                break;
            case 'c':
                MeshUtils.addSphereConstraintToCenter( rcs.getModifiedMesh(), 90 );
                break;
        }
    }

    public void startWithHemRing() {
        rcs = new HemRingCreator( this );
        ( ( HemRingCreator ) ( rcs ) ).generateMesh( 100, 0.2f );

        super.init();
    }

    public void startWithHerringStingerRing() {
        rcs = new HerringStingerRingCreator( this );
        ( ( HerringStingerRingCreator ) ( rcs ) ).generateMesh( 3, 60, 100 );

        super.init();
    }
}
