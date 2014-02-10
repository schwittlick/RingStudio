package main.gui;

import javax.swing.*;

/**
 * Author: mrzl
 * Date: 11.02.14
 * Time: 00:34
 * Project: MeshBrushDrawer
 */
public class TaskBarElement extends JFrame {
    public TaskBarElement( String title ) {
        super( title );
        setUndecorated( true );
        setVisible( true );
        setLocationRelativeTo( null );
    }
}