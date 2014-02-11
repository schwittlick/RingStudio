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

package main.rings;

import main.io.STLExporter;
import processing.core.PApplet;
import processing.core.PVector;
import toxi.geom.Vec3D;
import toxi.geom.mesh.WETriangleMesh;
import toxi.volume.*;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 16:12
 * Project: MeshBrushDrawer
 */
public abstract class RingCreator {

    protected VolumetricSpace volume;
    protected VolumetricBrush brush;
    protected IsoSurface surface;

    protected WETriangleMesh originalMesh;
    protected WETriangleMesh modifiedMesh;

    protected int spaceSize;
    protected int spaceResolution;
    protected double brushSize;

    protected PApplet parent;

    public RingCreator( PApplet _parent ) {
        this.parent = _parent;
        spaceSize = 2000;
        spaceResolution = 150;
        brushSize = 20.0;

        init();
    }

    protected void init() {
        this.originalMesh = new WETriangleMesh();
        this.modifiedMesh = new WETriangleMesh();

        this.volume = new VolumetricSpaceArray( new Vec3D( spaceSize, spaceSize, spaceSize ), spaceResolution, spaceResolution, spaceResolution );

        this.surface = new ArrayIsoSurface( volume );
        this.brush = new RoundBrush( volume, ( float ) ( brushSize ) );

    }

    protected void connectPoints( VolumetricBrush brush, PVector start, PVector end, float thickness ) {
        for ( float i = 0.0f; i <= 1; i += 0.1 ) {
            float lerpX = PApplet.lerp( start.x, end.x, i );
            float lerpY = PApplet.lerp( start.y, end.y, i );
            float lerpZ = PApplet.lerp( start.z, end.z, i );
            brush.drawAtAbsolutePos( new Vec3D( lerpX, lerpY, lerpZ ), thickness );
        }
    }

    public void exportMesh( String fileName ) {
        STLExporter.exportMesh( this.getModifiedMesh(), fileName );
    }

    protected void backupMesh() {
        modifiedMesh = originalMesh;
    }


    public WETriangleMesh getOriginalMesh() {


        return originalMesh;
    }


    public WETriangleMesh getModifiedMesh() {
        return modifiedMesh;
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

    public void recomputeMesh() {
        volume.closeSides();
        surface.reset();
        surface.computeSurfaceMesh( originalMesh, .1f );
        originalMesh.faceOutwards();
        originalMesh.removeUnusedVertices();
        originalMesh.rebuildIndex();
        originalMesh.computeFaceNormals();
        originalMesh.computeVertexNormals();

        backupMesh();
    }

}
