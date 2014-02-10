package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import toxi.geom.mesh.WETriangleMesh;
import toxi.volume.IsoSurface;
import toxi.volume.VolumetricBrush;
import toxi.volume.VolumetricSpace;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 16:14
 * Project: MeshBrushDrawer
 */
public class MeshCircle extends RingCreationStrategy {

    public MeshCircle( PApplet p ) {
        super( p );
    }


    public void drawMeshCircle( VolumetricSpace volume, IsoSurface surface, VolumetricBrush brush, float radius, float numPoints, WETriangleMesh mesh, float thickness ) {
        float angle = PConstants.TWO_PI / numPoints;
        for ( int i = 0; i < numPoints; i++ ) {
            PVector start = new PVector( radius * PApplet.sin( angle * i ), radius * PApplet.cos( angle * i ), 0 );
            PVector end = new PVector( radius * PApplet.sin( angle * ( i + 1 ) ), radius * PApplet.cos( angle * ( i + 1 ) ), 0 );
            connectPoints( brush, start, end, thickness );
        }
    }
}
