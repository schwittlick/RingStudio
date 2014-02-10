package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import toxi.geom.Vec3D;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 21:27
 * Project: MeshBrushDrawer
 */
public class ThreeLinesRing extends RingCreationStrategy {
    public ThreeLinesRing( PApplet p ) {
        super( p );

        init();
    }

    public void generateMesh( int nrOfRings, int distanceInbetween, float radius ) {

        init();
        for ( int i = 0; i < nrOfRings; i++ ) {
            drawMeshCircle( radius, 200, 0.2f, i * distanceInbetween );
            //getOriginalMesh().translate( new Vec3D( 0, 0, distanceInbetween ) );
        }

        recomputeMesh();
    }

    private void drawMeshCircle( float radius, float numPoints, float thickness, int zOffSet ) {
        float angle = PConstants.TWO_PI / numPoints;
        for ( int i = 0; i < numPoints; i++ ) {
            PVector start = new PVector( radius * PApplet.sin( angle * i ), radius * PApplet.cos( angle * i ), -zOffSet );
            PVector end = new PVector( radius * PApplet.sin( angle * ( i + 1 ) ), radius * PApplet.cos( angle * ( i + 1 ) ), -zOffSet );
            connectPoints( brush, start, end, thickness );
        }
    }

}
