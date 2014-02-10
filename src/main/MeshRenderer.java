package main;

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
