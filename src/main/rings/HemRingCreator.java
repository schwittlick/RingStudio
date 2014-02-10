package main.rings;

import main.misc.MeshUtils;
import main.io.MeshRenderer;
import main.RandomRingGeneratorDialog;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import toxi.geom.mesh.subdiv.MidpointSubdivision;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 16:11
 * Project: MeshBrushDrawer
 */
public class HemRingCreator extends RingCreationStrategy {
    private int growRingVariance;
    private int growRingHeight;
    private int growRingRadius;

    private RandomRingGeneratorDialog dialog;

    public HemRingCreator( PApplet p ) {
        super( p );

        this.growRingVariance = 4;
        this.growRingHeight = 800;
        this.growRingRadius = 100;


        this.dialog = new RandomRingGeneratorDialog( this );
        this.dialog.pack();
        this.dialog.setVisible( true );
    }

    public void generateMesh( float numPoints, float thickness ) {
        init();

        float angle = PConstants.TWO_PI / numPoints;
        int randomPoint = ( int ) parent.random( numPoints );
        for ( int height = 0; height < growRingHeight; height++ ) {
            randomPoint += ( int ) parent.random( -growRingVariance, growRingVariance );

            PVector start = new PVector( growRingRadius * PApplet.sin( angle * randomPoint ), growRingRadius * PApplet.cos( angle * randomPoint ), -height );
            PVector end = new PVector( growRingRadius * PApplet.sin( angle * ( randomPoint + 1 ) ), growRingRadius * PApplet.cos( angle * ( randomPoint + 1 ) ), -height + 1 );
            connectPoints( brush, start, end, thickness );
        }

        recomputeMesh();

        MeshUtils.smoothMesh( getModifiedMesh(), 10 );
    }


    public void setGrowRingVariance( int _growRingVariance ) {
        this.growRingVariance = _growRingVariance;
    }

    public void setGrowRingHeight( int _growRingHeight ) {
        this.growRingHeight = _growRingHeight;
    }

    public void setGrowRingRadius( int _growRingRadius ) {
        this.growRingRadius = _growRingRadius;
    }

    public void exportSTL() {
        String fileName = "ring-" + MeshUtils.getTimeStamp() + ".stl";

        exportMesh( fileName );
    }

    public void toggleWireFrame() {
        MeshRenderer.setWireframe( !MeshRenderer.isWireframe() );
    }

    public void toggleNormals() {
        MeshRenderer.setNormals( !MeshRenderer.isNormals() );
    }

    public void subdivideMesh() {
        try {
            getModifiedMesh().subdivide( new MidpointSubdivision(), 10 );
        } catch ( IllegalArgumentException e ) {
            // gangster coding :~)
        }
    }
}
