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

package main.io;

import processing.core.PApplet;
import toxi.geom.mesh.WETriangleMesh;
import toxi.processing.ToxiclibsSupport;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 18:12
 * Project: MeshBrushDrawer
 */
public class MeshRenderer {

    private ToxiclibsSupport gfx;
    PApplet parent;
    private static  boolean wireframe, normals;

    public MeshRenderer( PApplet _parent ) {
        this.parent = _parent;
        this.gfx = new ToxiclibsSupport( parent );
        this.wireframe = false;
    }


    public void render( WETriangleMesh mesh ) {
        if ( isWireframe() ) {
            parent.noFill();
            parent.stroke( 255 );
        } else {
            parent.noStroke();
            parent.fill( 255 );
        }

        this.gfx.mesh( mesh, normals, 3 );
    }

    public static void setWireframe( boolean _wireframe ) {

        MeshRenderer.wireframe = _wireframe;
    }

    public static boolean isWireframe() {
        return MeshRenderer.wireframe;
    }

    public static boolean isNormals() {
        return MeshRenderer.normals;
    }

    public static void setNormals( boolean _normals ) {
        MeshRenderer.normals = _normals;
    }
}
