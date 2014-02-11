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

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Author: mrzl
 * Date: 09.02.14
 * Time: 21:27
 * Project: MeshBrushDrawer
 */
public class HerringStingerRingCreator extends RingCreator {
    public HerringStingerRingCreator( PApplet p ) {
        super( p );

        init();
    }

    public void generateMesh( int nrOfRings, int distanceInbetween, float radius ) {

        init();
        for ( int i = 0; i < nrOfRings; i++ ) {
            drawMeshCircle( radius, 200, 0.2f, i * distanceInbetween );
        }

        drawConnection( radius, 0.2f, nrOfRings * distanceInbetween );

        recomputeMesh();
    }

    private void drawConnection( float radius, float thickness, int length ) {
        length *= 0.8f;
        int steps = 20;
        for ( int i = 0; i < steps; i++ ) {
            float lerpedZ = PApplet.lerp( 0, length - 1, i / ( float ) steps  );
            connectPoints( brush,
                    new PVector( radius * PApplet.sin( PConstants.TWO_PI / 2.7f ), radius * PApplet.cos( PConstants.TWO_PI / 2.7f ), -lerpedZ ),
                    new PVector( radius * PApplet.sin( PConstants.TWO_PI / 2.7f ), radius * PApplet.cos( PConstants.TWO_PI / 2.7f ), -lerpedZ + length / steps ), thickness );
        }
    }

    private void drawMeshCircle( float radius, float numPoints, float thickness, int zOffSet ) {
        float angle = PConstants.TWO_PI / numPoints;
        for ( int i = 0; i < numPoints; i++ ) {
            PVector start = new PVector( radius * PApplet.sin( angle * i ), radius * PApplet.cos( angle * i ), -zOffSet );
            PVector end = new PVector( radius * PApplet.sin( angle * ( i + 1 ) ), radius * PApplet.cos( angle * ( i + 1 ) ), -zOffSet );
            connectPoints( brush, start, end, thickness );
        }
    }
}
