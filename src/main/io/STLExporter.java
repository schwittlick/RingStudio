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
