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

package main;

import main.io.MeshRenderer;
import main.misc.MeshUtils;
import main.rings.HemRingCreator;
import main.rings.HerringStingerRingCreator;
import main.rings.RingCreator;
import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.mesh.LaplacianSmooth;

import java.util.EventListener;

/**
 * Author: mrzl
 * Date: 06.02.14
 * Time: 21:27
 * Project: MeshBrushDrawer
 */
public class MainRingFactory extends PApplet implements EventListener {

    private RingCreator rcs;
    private MeshRenderer meshRenderer;
    private PeasyCam cam;


    public void init() {
        RingCreationChooserDialog dialog = new RingCreationChooserDialog( this );
        dialog.pack();
        dialog.setVisible( true );
    }

    /**
     * Just initializing some global objects.
     */
    public void setup() {
        //size( 1920, 1080, P3D );
        size( 1280, 720, P3D );

        this.meshRenderer = new MeshRenderer( this );

        this.cam = new PeasyCam( this, 1000 );
    }

    /**
     * Drawing the current mesh.
     */
    public void draw() {
        if ( this.frame != null )
            this.frame.setTitle( "FPS: " + ( int ) ( this.frameRate ) + " Vertices: " + rcs.getModifiedMesh().getNumVertices() );

        background( 0 );
        lights();

        meshRenderer.render( this.rcs.getModifiedMesh() );

    }

    public void keyPressed() {
        switch ( key ) {
            case 's':
                new LaplacianSmooth().filter( rcs.getModifiedMesh(), 1 );
                break;
            case 'e':
                rcs.exportMesh( "three_ring" + MeshUtils.getTimeStamp() + ".stl" );
                break;
            case 'c':
                MeshUtils.addSphereConstraintToCenter( rcs.getModifiedMesh(), 90 );
                break;
        }
    }

    public void startWithHemRing() {
        rcs = new HemRingCreator( this );
        ( ( HemRingCreator ) ( rcs ) ).generateMesh( 100, 0.2f );

        super.init();
    }

    public void startWithHerringStingerRing() {
        rcs = new HerringStingerRingCreator( this );
        ( ( HerringStingerRingCreator ) ( rcs ) ).generateMesh( 3, 60, 100 );

        super.init();
    }
}
