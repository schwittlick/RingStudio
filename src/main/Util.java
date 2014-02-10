package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import toxi.geom.Vec3D;
import toxi.geom.mesh.LaplacianSmooth;
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


    public static void smoothMesh( WETriangleMesh mesh, int amount ) {
        new LaplacianSmooth().filter( mesh, amount );
    }
}
