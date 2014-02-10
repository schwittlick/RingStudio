package main;

import main.gui.TaskBarElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RingCreationChooserDialog extends JDialog {
    private JPanel contentPane;
    private JButton hemRingButton;
    private JButton herringStingerButton;

    public RingCreationChooserDialog( final MainRingFactory mainRingFactory ) {

        super( new TaskBarElement( "Name on task bar" ) );

        setContentPane( contentPane );
        setModal( true );
        //getRootPane().setDefaultButton( buttonOK );

        hemRingButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                mainRingFactory.startWithHemRing();
                dispose();
            }
        } );

        herringStingerButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                mainRingFactory.startWithHerringStingerRing();
                dispose();
            }
        } );

    }

    private void onOK() {
// add your code here
        dispose();
    }

    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( !visible ) {
            ( ( TaskBarElement ) getParent() ).dispose();
        }
    }

}
