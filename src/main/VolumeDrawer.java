package main;

import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;
import toxi.geom.mesh.LaplacianSmooth;
import toxi.geom.mesh.WETriangleMesh;
import toxi.processing.ToxiclibsSupport;
import toxi.volume.*;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

/**
 * Author: mrzl
 * Date: 06.02.14
 * Time: 21:27
 * Project: MeshBrushDrawer
 */
public class VolumeDrawer extends PApplet {

    private ToxiclibsSupport gfx;
    private PeasyCam cam;
    private VolumeDrawerGUI dialog;

    private VolumetricSpace volume;
    private VolumetricBrush brush;
    private IsoSurface surface;

    private WETriangleMesh originalMesh;
    private WETriangleMesh modifiedMesh;

    public void setup() {
        //size( 1920, 1080, P3D );
        size( 1280, 720, P3D );


        this.gfx = new ToxiclibsSupport( this );
        this.cam = new PeasyCam( this, 1000 );


        this.originalMesh = new WETriangleMesh();
        this.modifiedMesh = new WETriangleMesh();

        initDrawingTools( 2000, 150, 20.0 );

        // Util.drawMeshCircle( this.volume, this.surface, this.brush, 500, 100, this.originalMesh, 0.2f );
        Util.drawRandomGrowRing( this, volume, surface, brush, 100, 100, originalMesh, 0.2f );
        modifiedMesh = originalMesh;

        dialog = new VolumeDrawerGUI( this );
        dialog.pack();
        dialog.setVisible( true );
    }


    private void initDrawingTools( int spaceSize, int spaceResolution, double brushSize ) {
        this.volume = new VolumetricSpaceArray( new Vec3D( spaceSize, spaceSize, spaceSize ), spaceResolution, spaceResolution, spaceResolution );

        this.surface = new ArrayIsoSurface( volume );
        this.brush = new RoundBrush( volume, ( float ) ( brushSize ) );
    }

    public WETriangleMesh getOriginalMesh() {
        return originalMesh;
    }

    public void saveMesh() {
        String fileName = "ring-" + getTimeStamp() + ".stl";

        modifiedMesh.faceOutwards();
        modifiedMesh.removeUnusedVertices();
        modifiedMesh.rebuildIndex();
        modifiedMesh.computeFaceNormals();
        modifiedMesh.computeVertexNormals();

        modifiedMesh.saveAsSTL( fileName );
    }

    public void scale( WETriangleMesh mesh, Vec3D.Axis axis, float scaleFactorX, float scaleFactorY, float scaleFactorZ ) {
        scaleFactorX /= 10.0;
        scaleFactorY /= 10.0;
        scaleFactorZ /= 10.0;
        switch ( axis ) {
            case X:
                modifiedMesh = mesh.getScaled( new Vec3D( scaleFactorX, scaleFactorY, scaleFactorZ ) );
                break;
            case Y:
                modifiedMesh = mesh.getScaled( new Vec3D( scaleFactorX, scaleFactorY, scaleFactorZ ) );
                break;
            case Z:
                modifiedMesh = mesh.getScaled( new Vec3D( scaleFactorX, scaleFactorY, scaleFactorZ ) );
                break;
        }
    }


    public void draw() {
        if ( this.frame != null )
            frame.setTitle( "FPS: " + ( int ) ( this.frameRate ) + " Vertices: " + this.originalMesh.getNumVertices() );

        background( 0 );
        lights();
        noStroke();

        this.gfx.mesh( this.modifiedMesh, false );
    }

    public void keyPressed() {
        switch ( key ) {
            case 'l':
                new LaplacianSmooth().filter( this.modifiedMesh, 1 );
                break;
            case 's':
                String fileName = "saved/volume-drawer-" + Util.getTimeStamp() + ".png";
                saveFrame( fileName );
                System.out.println( "Saved " + fileName );
                break;
            case 't':
                saveMesh();
        }
    }

    public String getTimeStamp() {
        Calendar now = Calendar.getInstance();
        return String.format( "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now );
    }
}
