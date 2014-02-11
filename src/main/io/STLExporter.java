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

import toxi.geom.mesh.WETriangleMesh;

/**
 * Author: mrzl
 * Date: 11.02.14
 * Time: 00:29
 * Project: MeshBrushDrawer
 */
public class STLExporter {
    public static void exportMesh( WETriangleMesh mesh, String fileName ) {
        mesh.faceOutwards();
        mesh.removeUnusedVertices();
        mesh.rebuildIndex();
        mesh.computeFaceNormals();
        mesh.computeVertexNormals();

        mesh.scale( 0.15f );

        mesh.saveAsSTL( fileName );

        mesh.scale( 6.6666f );
    }
}
