package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import toxi.geom.Vec3D;
import toxi.geom.mesh.WETriangleMesh;
import toxi.volume.*;

import java.util.Calendar;

/**
 * Author: mrzl
 * Date: 06.02.14
 * Time: 21:44
 * Project: MeshBrushDrawer
 */
public class Util {
    public static void drawMeshCircle( VolumetricSpace volume, IsoSurface surface, VolumetricBrush brush, float radius, float numPoints, WETriangleMesh mesh, float thickness ) {
        float angle = PConstants.TWO_PI / numPoints;
        for ( int i = 0; i < numPoints; i++ ) {
            PVector start = new PVector( radius * PApplet.sin( angle * i ), radius * PApplet.cos( angle * i ), 0 );
            PVector end = new PVector( radius * PApplet.sin( angle * ( i + 1 ) ), radius * PApplet.cos( angle * ( i + 1 ) ), 0 );
            connectPoints( brush, start, end, thickness );
        }
    }

    public static void drawRandomGrowRing( PApplet parent, VolumetricSpace volume, IsoSurface surface, VolumetricBrush brush, float radius, float numPoints, WETriangleMesh mesh, float thickness ) {
        float angle = PConstants.TWO_PI / numPoints;
        int randomPoint = ( int ) parent.random( numPoints );
        for ( int height = 0; height < 500; height++ ) {
            randomPoint += ( int ) parent.random( -5, 5 );

            PVector start = new PVector( radius * PApplet.sin( angle * randomPoint ), radius * PApplet.cos( angle * randomPoint ), -height );
            PVector end = new PVector( radius * PApplet.sin( angle * ( randomPoint + 1 ) ), radius * PApplet.cos( angle * ( randomPoint + 1 ) ), -height + 1 );
            connectPoints( brush, start, end, thickness );
        }

        volume.closeSides();
        surface.reset();
        surface.computeSurfaceMesh( mesh, .1f );
        mesh.faceOutwards();
        mesh.removeUnusedVertices();
        mesh.rebuildIndex();
        mesh.computeFaceNormals();
        mesh.computeVertexNormals();
    }

    public static void connectPoints( VolumetricBrush brush, PVector start, PVector end, float thickness ) {
        for ( float i = 0.0f; i <= 1; i += 0.1 ) {
            float lerpX = PApplet.lerp( start.x, end.x, i );
            float lerpY = PApplet.lerp( start.y, end.y, i );
            float lerpZ = PApplet.lerp( start.z, end.z, i );
            brush.drawAtAbsolutePos( new Vec3D( lerpX, lerpY, lerpZ ), thickness );
        }
    }

    public static WETriangleMesh meshVoxelizer( WETriangleMesh mesh, int meshVoxRes,
                                                int wallThickness, float surfaceIso ) {
        MeshVoxelizer voxelizer = new MeshVoxelizer( meshVoxRes );
        voxelizer.setWallThickness( wallThickness );
        VolumetricSpace vol = voxelizer.voxelizeMesh( mesh );
        vol.closeSides();
        IsoSurface surface = new HashIsoSurface( vol );
        mesh = new WETriangleMesh();
        surface.computeSurfaceMesh( mesh, surfaceIso );
        mesh.faceOutwards();
        mesh.computeFaceNormals();
        mesh.computeVertexNormals();
        return mesh;
    }

    public static String getTimeStamp() {
        Calendar now = Calendar.getInstance();
        return String.format( "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now );
    }
}
