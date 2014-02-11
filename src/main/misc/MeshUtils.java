/*
 * RingStudio
 *
 * Copyright (C) 2014 Marcel Schwittlick
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * {@link http://www.gnu.org/licenses/lgpl.html}
 *
 */

package main.misc;

import toxi.geom.Vec3D;
import toxi.geom.mesh.LaplacianSmooth;
import toxi.geom.mesh.Vertex;
import toxi.geom.mesh.WETriangleMesh;
import toxi.physics.VerletParticle;
import toxi.physics.constraints.SphereConstraint;
import toxi.volume.HashIsoSurface;
import toxi.volume.IsoSurface;
import toxi.volume.MeshVoxelizer;
import toxi.volume.VolumetricSpace;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Author: mrzl
 * Date: 06.02.14
 * Time: 21:44
 * Project: MeshBrushDrawer
 */
public class MeshUtils {


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

    public static void addSphereConstraintToCenter( WETriangleMesh mesh, int radius ) {
        System.out.println( "Applying Sphere Constraint." );
        ArrayList<SphereConstraint> sphereConstraints = new ArrayList<SphereConstraint>();
        for( int i = 0; i < 100; i++ ) {
            sphereConstraints.add( new SphereConstraint( new Vec3D( 0, 0, -i * 5 ), radius, false ) );
        }

        for ( Vertex v : mesh.getVertices() ) {

            VerletParticle p = new VerletParticle( v.x(), v.y(), v.z() );
            for( SphereConstraint c : sphereConstraints ) {
                p.addConstraint( c );
            }

            p.applyConstraints();
            v.setComponent( Vec3D.Axis.X, p.x() );
            v.setComponent( Vec3D.Axis.Y, p.y() );
            v.setComponent( Vec3D.Axis.Z, p.z() );
        }
    }
}
